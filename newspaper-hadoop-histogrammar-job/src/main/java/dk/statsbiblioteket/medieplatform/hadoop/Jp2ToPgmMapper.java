package dk.statsbiblioteket.medieplatform.hadoop;

import dk.statsbiblioteket.medieplatform.autonomous.ConfigConstants;
import dk.statsbiblioteket.util.console.ProcessRunner;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Input is line-number, jpeg2k path.
 * Output is jpeg2k path, pgm path
 */
public class Jp2ToPgmMapper extends Mapper<LongWritable, Text, Text, Text> {

    private static Logger log = Logger.getLogger(Jp2ToPgmMapper.class);
    private String batchID;
    private String commandPath;
    private String outputFolder;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        batchID = context.getConfiguration().get(ConfigConstants.BATCH_ID);
        commandPath = context.getConfiguration().get(ConfigConstants.KAKADU_PATH);
        outputFolder = context.getConfiguration().get(HistogrammarJob.TEMP_FOLDER);
    }

    /**
     * run command on the given file
     *
     * @param dataPath    the path to the jp2 file
     *
     * @return the path to the converted file
     * @throws java.io.IOException if the execution of jpylyzer failed in some fashion (not invalid file, if the
     *                             program
     *                             returned non-zero returncode)
     */
    protected File convert(String dataPath) throws IOException {


        File resultPath = getConvertedPath(dataPath);
        String[] commandLine = makeCommandLine(dataPath, commandPath, resultPath);
        ProcessRunner runner = new ProcessRunner(commandLine);

        log.debug("Running command '" + Arrays.deepToString(commandLine) + "'");
        Map<String, String> myEnv = new HashMap<String, String>(System.getenv());
        runner.setEnviroment(myEnv);
        runner.setOutputCollectionByteSize(Integer.MAX_VALUE);

        //this call is blocking
        runner.run();

        if (runner.getReturnCode() == 0) {
            return resultPath;
        } else {
            String message
                    = "failed to run, returncode:" + runner.getReturnCode() + ", stdOut:" + runner.getProcessOutputAsString() + " stdErr:" + runner
                    .getProcessErrorAsString();
            throw new IOException(message);
        }
    }

    private String[] makeCommandLine(String dataPath, String commandPath, File resultFile) {

        String[] commandBits = commandPath.split(" ");
        List<String> commandList = Arrays.asList(commandBits);
        ArrayList<String> result = new ArrayList<String>(commandList);
        result.addAll(
                Arrays.asList(
                        "-i", dataPath, "-o", resultFile.getAbsolutePath()));
        return result.toArray(new String[result.size()]);
    }

    private File getConvertedPath(String dataPath) {
        File batchFolder = new File(outputFolder, batchID);
        batchFolder.mkdirs();
        return new File(batchFolder, new File(dataPath+".pgm").getName());
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        try {
            log.debug("Mapping for '"+key+"' and '"+value+"'");
            File pgmPath = convert(value.toString());
            context.write(value, new Text(pgmPath.getAbsolutePath()));
        } catch (Exception e) {
            log.error(e);
            throw new IOException(e);
        }
    }

}
