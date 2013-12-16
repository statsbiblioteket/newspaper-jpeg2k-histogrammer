package dk.statsbiblioteket.medieplatform.hadoop;

import dk.statsbiblioteket.doms.central.connectors.BackendInvalidCredsException;
import dk.statsbiblioteket.doms.central.connectors.BackendInvalidResourceException;
import dk.statsbiblioteket.doms.central.connectors.BackendMethodFailedException;
import dk.statsbiblioteket.doms.central.connectors.EnhancedFedora;
import dk.statsbiblioteket.doms.central.connectors.EnhancedFedoraImpl;
import dk.statsbiblioteket.doms.central.connectors.fedora.pidGenerator.PIDGeneratorException;
import dk.statsbiblioteket.doms.webservices.authentication.Credentials;
import dk.statsbiblioteket.medieplatform.autonomous.ConfigConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This reducer saves the result in doms
 * The input is filepath, jpylyzer xml output
 * The output is filepath, domspid
 */
public class DomsSaverReducer extends Reducer<Text, Text, Text, Text> {


    private static Logger log = Logger.getLogger(DomsSaverReducer.class);
    private EnhancedFedora fedora;
    private String batchID = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        fedora = createFedoraClient(context);
        batchID = context.getConfiguration()
                         .get(ConfigConstants.BATCH_ID);
    }

    /**
     * Get the fedora client
     *
     * @param context the hadoop context
     *
     * @return the fedora client
     * @throws java.io.IOException
     */
    protected EnhancedFedora createFedoraClient(Context context) throws IOException {
        try {
            Configuration conf = context.getConfiguration();

            String username = conf.get(ConfigConstants.DOMS_USERNAME);
            String password = conf.get(ConfigConstants.DOMS_PASSWORD);
            String domsUrl = conf.get(ConfigConstants.DOMS_URL);
            return new EnhancedFedoraImpl(
                    new Credentials(username, password), domsUrl, null, null);
        } catch (JAXBException e) {
            throw new IOException(e);
        } catch (PIDGeneratorException e) {
            throw new IOException(e);
        }
    }

    /**
     * Reduce (save result in doms)
     *
     * @param key the filename
     * @param values the jpylyzer xml
     * @param context the task context
     *
     * @throws java.io.IOException Any checked exception that is not an InterruptedException
     * @throws InterruptedException from Hadoop
     */
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        try {
            log.debug("Reduce for file '" + key + "'");
            String pid = getDomsPid(key);
            log.debug("Found doms pid '" + pid + "' for key '" + key + "'");
            String translate = translate(key.toString());
            log.debug("Translated filename '" + key + "'to '" + translate + "'");

            boolean first = false;
            for (Text value : values) {
                if (!first) {
                    first = true;
                } else {
                    log.error("Found multiple histogram results for file '" + translate + "'");
                    throw new RuntimeException("Found multiple histogram results for file '" + translate + "'");
                }
                log.info("Stored jpylyzer output for file '"+translate+"' in object '"+pid+"'");
                fedora.modifyDatastreamByValue(
                        pid,
                        "HISTOGRAM",
                        value.toString(),
                        Arrays.asList(translate + ".histogram.xml"),
                        "added histogram data from Hadoop");
                context.write(key, new Text(pid));
            }
        } catch (BackendInvalidCredsException e) {
            log.error(e);
            throw new IOException(e);
        } catch (BackendMethodFailedException e) {
            log.error(e);
            throw new IOException(e);
        } catch (BackendInvalidResourceException e) {
            log.error(e);
            throw new IOException(e);
        }


    }

    /**
     * Get the doms pid from the filename
     *
     * @param key the filename
     *
     * @return the doms pid
     * @throws java.io.IOException
     */
    private String getDomsPid(Text key) throws BackendInvalidCredsException, BackendMethodFailedException {

        String filePath = translate(key.toString());
        String path = "path:" + filePath;
        List<String> hits = fedora.findObjectFromDCIdentifier(path);
        if (hits.isEmpty()) {

            throw new RuntimeException("Failed to look up doms object for DC identifier '" + path + "'");
        } else {
            if (hits.size() > 1) {
                log.warn("Found multipe pids for dc identifier '" + path + "'");
            }
            return hits.get(0);
        }

    }

    /**
     * Translate the filename back to the original path as stored in doms
     *
     * @param file the filename
     *
     * @return the original path
     */
    private String translate(String file) {
        return file.substring(file.indexOf(batchID))
                   .replaceAll("_", "/");
    }


}
