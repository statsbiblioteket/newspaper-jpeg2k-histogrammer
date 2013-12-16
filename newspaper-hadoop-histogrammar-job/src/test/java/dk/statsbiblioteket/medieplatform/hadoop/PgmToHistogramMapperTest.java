package dk.statsbiblioteket.medieplatform.hadoop;

import dk.statsbiblioteket.util.Files;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PgmToHistogramMapperTest {


    @Test
    public void testRealConvert() throws IOException, URISyntaxException {

        MapDriver<LongWritable, Text, Text, Text> mapDriver;

        PgmToHistogramMapper mapper = new PgmToHistogramMapper();
        String name = "balloon.jp2.pgm";
        String testFile = getAbsolutePath(name);
        File workingFile = new File(testFile + ".temp.pgm");
        Files.copy(new File(testFile), workingFile,true);
        mapDriver = MapDriver.newMapDriver(mapper);


        mapDriver.withInput(new LongWritable(1), new Text(workingFile.getAbsolutePath()));

        mapDriver.withOutput(new Text(workingFile.getAbsolutePath()), new Text("<histogram xmlns=\"http://www.statsbiblioteket.dk/avisdigitalisering/histogram/1/0/\"><colorScheme><colorSpace>Greyscale</colorSpace><colorDepth>8 bits</colorDepth></colorScheme><colors><color><code>0</code><count>4717</count></color><color><code>1</code><count>1292</count></color><color><code>2</code><count>1659</count></color><color><code>3</code><count>1911</count></color><color><code>4</code><count>2077</count></color><color><code>5</code><count>1983</count></color><color><code>6</code><count>1975</count></color><color><code>7</code><count>2071</count></color><color><code>8</code><count>2057</count></color><color><code>9</code><count>2234</count></color><color><code>10</code><count>2349</count></color><color><code>11</code><count>2510</count></color><color><code>12</code><count>2560</count></color><color><code>13</code><count>2824</count></color><color><code>14</code><count>2840</count></color><color><code>15</code><count>3009</count></color><color><code>16</code><count>3106</count></color><color><code>17</code><count>3260</count></color><color><code>18</code><count>3392</count></color><color><code>19</code><count>3547</count></color><color><code>20</code><count>3539</count></color><color><code>21</code><count>3773</count></color><color><code>22</code><count>3963</count></color><color><code>23</code><count>4009</count></color><color><code>24</code><count>4286</count></color><color><code>25</code><count>4635</count></color><color><code>26</code><count>4622</count></color><color><code>27</code><count>4715</count></color><color><code>28</code><count>5004</count></color><color><code>29</code><count>5182</count></color><color><code>30</code><count>5313</count></color><color><code>31</code><count>5514</count></color><color><code>32</code><count>5572</count></color><color><code>33</code><count>5879</count></color><color><code>34</code><count>6004</count></color><color><code>35</code><count>6294</count></color><color><code>36</code><count>6382</count></color><color><code>37</code><count>6543</count></color><color><code>38</code><count>6778</count></color><color><code>39</code><count>6977</count></color><color><code>40</code><count>7166</count></color><color><code>41</code><count>7240</count></color><color><code>42</code><count>7659</count></color><color><code>43</code><count>7705</count></color><color><code>44</code><count>8015</count></color><color><code>45</code><count>8264</count></color><color><code>46</code><count>8375</count></color><color><code>47</code><count>8692</count></color><color><code>48</code><count>8774</count></color><color><code>49</code><count>9127</count></color><color><code>50</code><count>9359</count></color><color><code>51</code><count>9565</count></color><color><code>52</code><count>9621</count></color><color><code>53</code><count>10009</count></color><color><code>54</code><count>10362</count></color><color><code>55</code><count>10621</count></color><color><code>56</code><count>10674</count></color><color><code>57</code><count>11076</count></color><color><code>58</code><count>11179</count></color><color><code>59</code><count>11441</count></color><color><code>60</code><count>11899</count></color><color><code>61</code><count>12045</count></color><color><code>62</code><count>12494</count></color><color><code>63</code><count>12457</count></color><color><code>64</code><count>12933</count></color><color><code>65</code><count>13145</count></color><color><code>66</code><count>13545</count></color><color><code>67</code><count>13876</count></color><color><code>68</code><count>14105</count></color><color><code>69</code><count>14491</count></color><color><code>70</code><count>14536</count></color><color><code>71</code><count>14759</count></color><color><code>72</code><count>15290</count></color><color><code>73</code><count>15569</count></color><color><code>74</code><count>15637</count></color><color><code>75</code><count>16115</count></color><color><code>76</code><count>16244</count></color><color><code>77</code><count>16659</count></color><color><code>78</code><count>16935</count></color><color><code>79</code><count>17190</count></color><color><code>80</code><count>16982</count></color><color><code>81</code><count>17766</count></color><color><code>82</code><count>17951</count></color><color><code>83</code><count>18345</count></color><color><code>84</code><count>18269</count></color><color><code>85</code><count>18781</count></color><color><code>86</code><count>18820</count></color><color><code>87</code><count>19341</count></color><color><code>88</code><count>19587</count></color><color><code>89</code><count>19787</count></color><color><code>90</code><count>19897</count></color><color><code>91</code><count>20086</count></color><color><code>92</code><count>20500</count></color><color><code>93</code><count>20743</count></color><color><code>94</code><count>20971</count></color><color><code>95</code><count>21259</count></color><color><code>96</code><count>21027</count></color><color><code>97</code><count>21293</count></color><color><code>98</code><count>21672</count></color><color><code>99</code><count>21277</count></color><color><code>100</code><count>21984</count></color><color><code>101</code><count>21997</count></color><color><code>102</code><count>21845</count></color><color><code>103</code><count>22107</count></color><color><code>104</code><count>22518</count></color><color><code>105</code><count>22368</count></color><color><code>106</code><count>22936</count></color><color><code>107</code><count>22672</count></color><color><code>108</code><count>22699</count></color><color><code>109</code><count>23128</count></color><color><code>110</code><count>23056</count></color><color><code>111</code><count>22853</count></color><color><code>112</code><count>23192</count></color><color><code>113</code><count>23345</count></color><color><code>114</code><count>23353</count></color><color><code>115</code><count>23299</count></color><color><code>116</code><count>23609</count></color><color><code>117</code><count>23148</count></color><color><code>118</code><count>23614</count></color><color><code>119</code><count>23666</count></color><color><code>120</code><count>23779</count></color><color><code>121</code><count>23638</count></color><color><code>122</code><count>23508</count></color><color><code>123</code><count>23181</count></color><color><code>124</code><count>23260</count></color><color><code>125</code><count>23456</count></color><color><code>126</code><count>23570</count></color><color><code>127</code><count>23479</count></color><color><code>128</code><count>23209</count></color><color><code>129</code><count>23183</count></color><color><code>130</code><count>23454</count></color><color><code>131</code><count>23094</count></color><color><code>132</code><count>23154</count></color><color><code>133</code><count>23042</count></color><color><code>134</code><count>23170</count></color><color><code>135</code><count>23100</count></color><color><code>136</code><count>22847</count></color><color><code>137</code><count>23110</count></color><color><code>138</code><count>22706</count></color><color><code>139</code><count>22549</count></color><color><code>140</code><count>22124</count></color><color><code>141</code><count>22353</count></color><color><code>142</code><count>21959</count></color><color><code>143</code><count>22096</count></color><color><code>144</code><count>21827</count></color><color><code>145</code><count>21592</count></color><color><code>146</code><count>21718</count></color><color><code>147</code><count>21513</count></color><color><code>148</code><count>21407</count></color><color><code>149</code><count>21108</count></color><color><code>150</code><count>20464</count></color><color><code>151</code><count>21032</count></color><color><code>152</code><count>20512</count></color><color><code>153</code><count>20502</count></color><color><code>154</code><count>20518</count></color><color><code>155</code><count>20311</count></color><color><code>156</code><count>20374</count></color><color><code>157</code><count>20046</count></color><color><code>158</code><count>19614</count></color><color><code>159</code><count>19957</count></color><color><code>160</code><count>19658</count></color><color><code>161</code><count>19536</count></color><color><code>162</code><count>19265</count></color><color><code>163</code><count>19142</count></color><color><code>164</code><count>19079</count></color><color><code>165</code><count>19059</count></color><color><code>166</code><count>18677</count></color><color><code>167</code><count>18589</count></color><color><code>168</code><count>18503</count></color><color><code>169</code><count>18453</count></color><color><code>170</code><count>18382</count></color><color><code>171</code><count>18400</count></color><color><code>172</code><count>18170</count></color><color><code>173</code><count>18065</count></color><color><code>174</code><count>18099</count></color><color><code>175</code><count>18084</count></color><color><code>176</code><count>17965</count></color><color><code>177</code><count>17816</count></color><color><code>178</code><count>17875</count></color><color><code>179</code><count>17696</count></color><color><code>180</code><count>17662</count></color><color><code>181</code><count>17755</count></color><color><code>182</code><count>17774</count></color><color><code>183</code><count>17647</count></color><color><code>184</code><count>17836</count></color><color><code>185</code><count>17814</count></color><color><code>186</code><count>17695</count></color><color><code>187</code><count>17886</count></color><color><code>188</code><count>17774</count></color><color><code>189</code><count>18069</count></color><color><code>190</code><count>18104</count></color><color><code>191</code><count>18447</count></color><color><code>192</code><count>18395</count></color><color><code>193</code><count>18697</count></color><color><code>194</code><count>18954</count></color><color><code>195</code><count>19277</count></color><color><code>196</code><count>19296</count></color><color><code>197</code><count>19716</count></color><color><code>198</code><count>20259</count></color><color><code>199</code><count>20357</count></color><color><code>200</code><count>20845</count></color><color><code>201</code><count>21385</count></color><color><code>202</code><count>21753</count></color><color><code>203</code><count>22237</count></color><color><code>204</code><count>22573</count></color><color><code>205</code><count>23339</count></color><color><code>206</code><count>23706</count></color><color><code>207</code><count>24674</count></color><color><code>208</code><count>25568</count></color><color><code>209</code><count>26576</count></color><color><code>210</code><count>27262</count></color><color><code>211</code><count>28853</count></color><color><code>212</code><count>30356</count></color><color><code>213</code><count>32436</count></color><color><code>214</code><count>34354</count></color><color><code>215</code><count>36594</count></color><color><code>216</code><count>39140</count></color><color><code>217</code><count>42558</count></color><color><code>218</code><count>46114</count></color><color><code>219</code><count>50480</count></color><color><code>220</code><count>54565</count></color><color><code>221</code><count>60161</count></color><color><code>222</code><count>66219</count></color><color><code>223</code><count>73941</count></color><color><code>224</code><count>83368</count></color><color><code>225</code><count>96385</count></color><color><code>226</code><count>113105</count></color><color><code>227</code><count>133694</count></color><color><code>228</code><count>161763</count></color><color><code>229</code><count>198541</count></color><color><code>230</code><count>238437</count></color><color><code>231</code><count>283776</count></color><color><code>232</code><count>330375</count></color><color><code>233</code><count>375351</count></color><color><code>234</code><count>401586</count></color><color><code>235</code><count>420472</count></color><color><code>236</code><count>434105</count></color><color><code>237</code><count>434505</count></color><color><code>238</code><count>425142</count></color><color><code>239</code><count>401976</count></color><color><code>240</code><count>374421</count></color><color><code>241</code><count>332571</count></color><color><code>242</code><count>284613</count></color><color><code>243</code><count>222042</count></color><color><code>244</code><count>150661</count></color><color><code>245</code><count>92123</count></color><color><code>246</code><count>48521</count></color><color><code>247</code><count>24659</count></color><color><code>248</code><count>12852</count></color><color><code>249</code><count>6444</count></color><color><code>250</code><count>3665</count></color><color><code>251</code><count>2214</count></color><color><code>252</code><count>1320</count></color><color><code>253</code><count>903</count></color><color><code>254</code><count>652</count></color><color><code>255</code><count>1294</count></color></colors></histogram>"));
        mapDriver.runTest();
    }

    private String getAbsolutePath(String name) throws URISyntaxException {
        return new File(Thread.currentThread().getContextClassLoader().getResource(
                name).toURI()).getAbsolutePath();
    }


}
