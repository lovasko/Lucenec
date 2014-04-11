package sk.lovasko.lucenec.color;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.HashMap;

public final class RGB
{
	private final double red;
	private final double green;
	private final double blue;

	private static final Map<String, RGB> color_names;

	static
	{
		color_names = new HashMap<String, RGB>();
		color_names.put("green_beige", new RGB(0.7450980392, 0.7411764705, 0.4980392156));
		color_names.put("beige", new RGB(0.7607843137, 0.6901960784, 0.4705882352));
		color_names.put("sand_yellow", new RGB(0.7764705882, 0.6509803921, 0.3921568627));
		color_names.put("signal_yellow", new RGB(0.8980392156, 0.7450980392, 0.0039215686));
		color_names.put("golden_yellow", new RGB(0.8039215686, 0.6431372549, 0.2039215686));
		color_names.put("honey_yellow", new RGB(0.6627450980, 0.5137254901, 0.0274509803));
		color_names.put("maize_yellow", new RGB(0.8941176470, 0.6274509803, 0.0627450980));
		color_names.put("daffodil_yellow", new RGB(0.8627450980, 0.6117647058, 0));
		color_names.put("brown_beige", new RGB(0.5411764705, 0.4000000000, 0.2588235294));
		color_names.put("lemon_yellow", new RGB(0.7803921568, 0.7058823529, 0.2745098039));
		color_names.put("oyster_white", new RGB(0.9176470588, 0.9019607843, 0.7921568627));
		color_names.put("ivory", new RGB(0.8823529411, 0.8000000000, 0.3098039215));
		color_names.put("light_ivory", new RGB(0.9019607843, 0.8392156862, 0.5647058823));
		color_names.put("sulfur_yellow", new RGB(0.9294117647, 1.0000000000, 0.1294117647));
		color_names.put("saffron_yellow", new RGB(0.9607843137, 0.8156862745, 0.2000000000));
		color_names.put("zinc_yellow", new RGB(0.9725490196, 0.9529411764, 0.2078431372));
		color_names.put("grey_beige", new RGB(0.6196078431, 0.5921568627, 0.3921568627));
		color_names.put("olive_yellow", new RGB(0.6000000000, 0.6000000000, 0.3137254901));
		color_names.put("rape_yellow", new RGB(0.9529411764, 0.8549019607, 0.0431372549));
		color_names.put("traffic_yellow", new RGB(0.9803921568, 0.8235294117, 0.0039215686));
		color_names.put("ochre_yellow", new RGB(0.6823529411, 0.6274509803, 0.2941176470));
		color_names.put("luminous_yellow", new RGB(1.0000000000, 1.0000000000, 0));
		color_names.put("curry", new RGB(0.6156862745, 0.5686274509, 0.0039215686));
		color_names.put("melon_yellow", new RGB(0.9568627450, 0.6627450980, 0));
		color_names.put("broom_yellow", new RGB(0.8392156862, 0.6823529411, 0.0039215686));
		color_names.put("dahlia_yellow", new RGB(0.9529411764, 0.6470588235, 0.0196078431));
		color_names.put("pastel_yellow", new RGB(0.9372549019, 0.6627450980, 0.2901960784));
		color_names.put("pearl_beige", new RGB(0.4156862745, 0.3647058823, 0.3019607843));
		color_names.put("pearl_gold", new RGB(0.4392156862, 0.3254901960, 0.2078431372));
		color_names.put("sun_yellow", new RGB(0.9529411764, 0.6235294117, 0.0941176470));
		color_names.put("yellow_orange", new RGB(0.9294117647, 0.4627450980, 0.0549019607));
		color_names.put("red_orange", new RGB(0.7882352941, 0.2352941176, 0.1254901960));
		color_names.put("vermilion", new RGB(0.7960784313, 0.1568627450, 0.1294117647));
		color_names.put("pastel_orange", new RGB(1.0000000000, 0.4588235294, 0.0784313725));
		color_names.put("pure_orange", new RGB(0.9568627450, 0.2745098039, 0.0666666666));
		color_names.put("luminous_orange", new RGB(1.0000000000, 0.1372549019, 0.0039215686));
		color_names.put("luminous_bright_orange", new RGB(1.0000000000, 0.6431372549, 0.1254901960));
		color_names.put("bright_red_orange", new RGB(0.9686274509, 0.3686274509, 0.1450980392));
		color_names.put("traffic_orange", new RGB(0.9607843137, 0.2509803921, 0.1294117647));
		color_names.put("signal_orange", new RGB(0.8470588235, 0.2941176470, 0.1254901960));
		color_names.put("deep_orange", new RGB(0.9254901960, 0.4862745098, 0.1490196078));
		color_names.put("salmon_range", new RGB(0.9215686274, 0.4156862745, 0.0549019607));
		color_names.put("pearl_orange", new RGB(0.7647058823, 0.3450980392, 0.1921568627));
		color_names.put("flame_red", new RGB(0.6862745098, 0.1686274509, 0.1176470588));
		color_names.put("signal_red", new RGB(0.6470588235, 0.1254901960, 0.0980392156));
		color_names.put("carmine_red", new RGB(0.6352941176, 0.1372549019, 0.1137254901));
		color_names.put("ruby_red", new RGB(0.6078431372, 0.0666666666, 0.1176470588));
		color_names.put("purple_red", new RGB(0.4588235294, 0.0823529411, 0.1176470588));
		color_names.put("wine_red", new RGB(0.3686274509, 0.1294117647, 0.1607843137));
		color_names.put("black_red", new RGB(0.2549019607, 0.1333333333, 0.1529411764));
		color_names.put("oxide_red", new RGB(0.3921568627, 0.1411764705, 0.1411764705));
		color_names.put("brown_red", new RGB(0.4705882352, 0.1215686274, 0.0980392156));
		color_names.put("beige_red", new RGB(0.7568627450, 0.5294117647, 0.4196078431));
		color_names.put("tomato_red", new RGB(0.6313725490, 0.1372549019, 0.0705882352));
		color_names.put("antique_pink", new RGB(0.8274509803, 0.4313725490, 0.4392156862));
		color_names.put("light_pink", new RGB(0.9176470588, 0.5372549019, 0.6039215686));
		color_names.put("coral_red", new RGB(0.7019607843, 0.1568627450, 0.1294117647));
		color_names.put("rose", new RGB(0.9019607843, 0.1960784313, 0.2666666666));
		color_names.put("strawberry_red", new RGB(0.8352941176, 0.1882352941, 0.1960784313));
		color_names.put("traffic_red", new RGB(0.8000000000, 0.0235294117, 0.0196078431));
		color_names.put("salmon_pink", new RGB(0.8509803921, 0.3137254901, 0.1882352941));
		color_names.put("luminous_red", new RGB(0.9725490196, 0, 0));
		color_names.put("luminous_bright_red", new RGB(0.9960784313, 0, 0));
		color_names.put("raspberry_red", new RGB(0.7725490196, 0.1137254901, 0.2039215686));
		color_names.put("pure_red", new RGB(0.7960784313, 0.1960784313, 0.2039215686));
		color_names.put("orient_red", new RGB(0.7019607843, 0.1411764705, 0.1568627450));
		color_names.put("pearl_uby_red", new RGB(0.4470588235, 0.0784313725, 0.1333333333));
		color_names.put("pearl_pink", new RGB(0.7058823529, 0.2980392156, 0.2627450980));
		color_names.put("red_lilac", new RGB(0.8705882352, 0.2980392156, 0.5411764705));
		color_names.put("red_violet", new RGB(0.5725490196, 0.1686274509, 0.2431372549));
		color_names.put("heather_violet", new RGB(0.8705882352, 0.2980392156, 0.5411764705));
		color_names.put("claret_violet", new RGB(0.4313725490, 0.1098039215, 0.2039215686));
		color_names.put("blue_lilac", new RGB(0.4235294117, 0.2745098039, 0.4588235294));
		color_names.put("traffic_purple", new RGB(0.6274509803, 0.2039215686, 0.4470588235));
		color_names.put("purple_violet", new RGB(0.2901960784, 0.0980392156, 0.1725490196));
		color_names.put("signal_violet", new RGB(0.5647058823, 0.2745098039, 0.5176470588));
		color_names.put("pastel_violet", new RGB(0.6431372549, 0.4901960784, 0.5647058823));
		color_names.put("telemagenta", new RGB(0.8431372549, 0.1764705882, 0.4274509803));
		color_names.put("pearl_violet", new RGB(0.5254901960, 0.4509803921, 0.6313725490));
		color_names.put("pearl_black_berry", new RGB(0.4235294117, 0.4078431372, 0.5058823529));
		color_names.put("violet_blue", new RGB(0.1647058823, 0.1803921568, 0.2941176470));
		color_names.put("green_blue", new RGB(0.1215686274, 0.2039215686, 0.2196078431));
		color_names.put("ultramarine_blue", new RGB(0.1254901960, 0.1294117647, 0.3098039215));
		color_names.put("saphire_blue", new RGB(0.1137254901, 0.1176470588, 0.2000000000));
		color_names.put("black_blue", new RGB(0.1254901960, 0.1294117647, 0.3098039215));
		color_names.put("signal_blue", new RGB(0.1176470588, 0.1764705882, 0.4313725490));
		color_names.put("brillant_blue", new RGB(0.2431372549, 0.3725490196, 0.5411764705));
		color_names.put("grey_blue", new RGB(0.1490196078, 0.1450980392, 0.1764705882));
		color_names.put("azure_blue", new RGB(0.0078431372, 0.3372549019, 0.4117647058));
		color_names.put("gentian_blue", new RGB(0.0549019607, 0.1607843137, 0.2941176470));
		color_names.put("steel_blue", new RGB(0.1372549019, 0.1019607843, 0.1411764705));
		color_names.put("light_blue", new RGB(0.2313725490, 0.5137254901, 0.7411764705));
		color_names.put("cobalt_blue", new RGB(0.1450980392, 0.1607843137, 0.2901960784));
		color_names.put("pigeon_blue", new RGB(0.3764705882, 0.4352941176, 0.5490196078));
		color_names.put("sky_blue", new RGB(0.1333333333, 0.4431372549, 0.7019607843));
		color_names.put("traffic_blue", new RGB(0.0235294117, 0.2235294117, 0.4431372549));
		color_names.put("turquoise_blue", new RGB(0.2470588235, 0.5333333333, 0.5607843137));
		color_names.put("capri_blue", new RGB(0.1058823529, 0.3333333333, 0.5137254901));
		color_names.put("ocean_blue", new RGB(0.1137254901, 0.2000000000, 0.2901960784));
		color_names.put("water_blue", new RGB(0.1450980392, 0.4274509803, 0.4823529411));
		color_names.put("night_blue", new RGB(0.1450980392, 0.1568627450, 0.3137254901));
		color_names.put("distant_blue", new RGB(0.2862745098, 0.4039215686, 0.5529411764));
		color_names.put("pastel_blue", new RGB(0.3647058823, 0.6078431372, 0.6078431372));
		color_names.put("pearl_gentian_blue", new RGB(0.1647058823, 0.3921568627, 0.4705882352));
		color_names.put("pearl_night_blue", new RGB(0.0627450980, 0.1725490196, 0.3294117647));
		color_names.put("patina_green", new RGB(0.1921568627, 0.4000000000, 0.3137254901));
		color_names.put("emerald_green", new RGB(0.1568627450, 0.4470588235, 0.2000000000));
		color_names.put("leaf_green", new RGB(0.1764705882, 0.3411764705, 0.1725490196));
		color_names.put("olive_green", new RGB(0.2588235294, 0.2745098039, 0.1960784313));
		color_names.put("blue_green", new RGB(0.1215686274, 0.2274509803, 0.2392156862));
		color_names.put("moss_green", new RGB(0.1843137254, 0.2705882352, 0.2196078431));
		color_names.put("grey_olive", new RGB(0.2431372549, 0.2313725490, 0.1960784313));
		color_names.put("bottle_green", new RGB(0.2039215686, 0.2313725490, 0.1607843137));
		color_names.put("brown green", new RGB(0.2235294117, 0.2078431372, 0.1647058823));
		color_names.put("fir_green", new RGB(0.1921568627, 0.2156862745, 0.1686274509));
		color_names.put("grass_green", new RGB(0.2078431372, 0.4078431372, 0.1764705882));
		color_names.put("reseda_green", new RGB(0.3450980392, 0.4470588235, 0.2745098039));
		color_names.put("black_green", new RGB(0.2039215686, 0.2431372549, 0.2509803921));
		color_names.put("reed_green", new RGB(0.4235294117, 0.4431372549, 0.3372549019));
		color_names.put("yellow_olive", new RGB(0.2784313725, 0.2509803921, 0.1803921568));
		color_names.put("black_olive", new RGB(0.2313725490, 0.2352941176, 0.2117647058));
		color_names.put("turquoise_green", new RGB(0.1176470588, 0.3490196078, 0.2705882352));
		color_names.put("may_green", new RGB(0.2980392156, 0.5686274509, 0.2549019607));
		color_names.put("yellow_green", new RGB(0.3411764705, 0.6509803921, 0.2235294117));
		color_names.put("pastel green", new RGB(0.7411764705, 0.9254901960, 0.7137254901));
		color_names.put("chrome green", new RGB(0.1803921568, 0.2274509803, 0.1372549019));
		color_names.put("pale_green", new RGB(0.5372549019, 0.6745098039, 0.4627450980));
		color_names.put("olive_drab", new RGB(0.1450980392, 0.1333333333, 0.1058823529));
		color_names.put("traffic_green", new RGB(0.1882352941, 0.5176470588, 0.2745098039));
		color_names.put("fern_green", new RGB(0.2392156862, 0.3921568627, 0.1764705882));
		color_names.put("opal_green", new RGB(0.0039215686, 0.3647058823, 0.3215686274));
		color_names.put("light_green", new RGB(0.5176470588, 0.7647058823, 0.7450980392));
		color_names.put("pine_green", new RGB(0.1725490196, 0.3333333333, 0.2705882352));
		color_names.put("mint_green", new RGB(0.1254901960, 0.3764705882, 0.2392156862));
		color_names.put("signal_green", new RGB(0.1921568627, 0.4980392156, 0.2627450980));
		color_names.put("mint_turquoise", new RGB(0.2862745098, 0.4941176470, 0.4627450980));
		color_names.put("pastel_turquoise", new RGB(0.4980392156, 0.7098039215, 0.7098039215));
		color_names.put("pearl_green", new RGB(0.1098039215, 0.3294117647, 0.1764705882));
		color_names.put("pearl_opal_green", new RGB(0.0862745098, 0.2078431372, 0.2156862745));
		color_names.put("pure_green", new RGB(0, 0.5607843137, 0.2235294117));
		color_names.put("luminous_green", new RGB(0, 0.7333333333, 0.1764705882));
		color_names.put("squirrel_grey", new RGB(0.4705882352, 0.5215686274, 0.5450980392));
		color_names.put("silver_grey", new RGB(0.5411764705, 0.5843137254, 0.5921568627));
		color_names.put("olive_grey", new RGB(0.4941176470, 0.4823529411, 0.3215686274));
		color_names.put("moss_grey", new RGB(0.4235294117, 0.4392156862, 0.3490196078));
		color_names.put("signal_grey", new RGB(0.5882352941, 0.6000000000, 0.5725490196));
		color_names.put("mouse_grey", new RGB(0.3921568627, 0.4196078431, 0.3882352941));
		color_names.put("beige_grey", new RGB(0.4274509803, 0.3960784313, 0.3215686274));
		color_names.put("khaki_grey", new RGB(0.4156862745, 0.3725490196, 0.1921568627));
		color_names.put("green_grey", new RGB(0.3019607843, 0.3372549019, 0.2705882352));
		color_names.put("tarpaulin_grey", new RGB(0.2980392156, 0.3176470588, 0.2901960784));
		color_names.put("iron_grey", new RGB(0.2627450980, 0.2941176470, 0.3019607843));
		color_names.put("basalt_grey", new RGB(0.3058823529, 0.3411764705, 0.3294117647));
		color_names.put("brown_grey", new RGB(0.2745098039, 0.2705882352, 0.1921568627));
		color_names.put("slate_grey", new RGB(0.2627450980, 0.2784313725, 0.3137254901));
		color_names.put("anthracite_grey", new RGB(0.1607843137, 0.1921568627, 0.2000000000));
		color_names.put("black_grey", new RGB(0.1372549019, 0.1568627450, 0.1686274509));
		color_names.put("umbra_grey", new RGB(0.2000000000, 0.1843137254, 0.1725490196));
		color_names.put("concrete_grey", new RGB(0.4078431372, 0.4235294117, 0.3686274509));
		color_names.put("graphite_grey", new RGB(0.2784313725, 0.2901960784, 0.3176470588));
		color_names.put("granite_grey", new RGB(0.1843137254, 0.2078431372, 0.2313725490));
		color_names.put("stone_grey", new RGB(0.5450980392, 0.5490196078, 0.4784313725));
		color_names.put("blue_grey", new RGB(0.2784313725, 0.2941176470, 0.3058823529));
		color_names.put("pebble_grey", new RGB(0.7215686274, 0.7176470588, 0.6000000000));
		color_names.put("cement_grey", new RGB(0.4901960784, 0.5176470588, 0.4431372549));
		color_names.put("yellow_grey", new RGB(0.5607843137, 0.5450980392, 0.4000000000));
		color_names.put("light_grey", new RGB(0.8431372549, 0.8431372549, 0.8431372549));
		color_names.put("platinum_grey", new RGB(0.4980392156, 0.4627450980, 0.4745098039));
		color_names.put("dusty_grey", new RGB(0.4901960784, 0.4980392156, 0.4705882352));
		color_names.put("agate_grey", new RGB(0.7647058823, 0.7647058823, 0.7647058823));
		color_names.put("quartz_grey", new RGB(0.4235294117, 0.4117647058, 0.3764705882));
		color_names.put("window_grey", new RGB(0.6156862745, 0.6313725490, 0.6666666666));
		color_names.put("traffic_grey_a", new RGB(0.5529411764, 0.5803921568, 0.5529411764));
		color_names.put("traffic_grey_b", new RGB(0.3058823529, 0.3294117647, 0.3215686274));
		color_names.put("silk_grey", new RGB(0.7921568627, 0.7686274509, 0.6901960784));
		color_names.put("telegrey_1", new RGB(0.5647058823, 0.5647058823, 0.5647058823));
		color_names.put("telegrey_2", new RGB(0.5098039215, 0.5372549019, 0.5607843137));
		color_names.put("telegrey_4", new RGB(0.8156862745, 0.8156862745, 0.8156862745));
		color_names.put("pearl_mouse_grey", new RGB(0.5372549019, 0.5058823529, 0.4627450980));
		color_names.put("green_brown", new RGB(0.5098039215, 0.4235294117, 0.2039215686));
		color_names.put("ochre_brown", new RGB(0.5843137254, 0.3725490196, 0.1254901960));
		color_names.put("signal_brown", new RGB(0.4235294117, 0.2313725490, 0.1647058823));
		color_names.put("clay_brown", new RGB(0.4509803921, 0.2588235294, 0.1333333333));
		color_names.put("copper_brown", new RGB(0.5568627450, 0.2509803921, 0.1647058823));
		color_names.put("fawn_brown", new RGB(0.3490196078, 0.2078431372, 0.1215686274));
		color_names.put("olive_brown", new RGB(0.4352941176, 0.3098039215, 0.1568627450));
		color_names.put("nut_brown", new RGB(0.3568627450, 0.2274509803, 0.1607843137));
		color_names.put("red_brown", new RGB(0.3490196078, 0.1372549019, 0.1294117647));
		color_names.put("sepia_brown", new RGB(0.2196078431, 0.1725490196, 0.1176470588));
		color_names.put("chestnut_brown", new RGB(0.3882352941, 0.2274509803, 0.2039215686));
		color_names.put("mahogany_brown", new RGB(0.2980392156, 0.1843137254, 0.1529411764));
		color_names.put("chocolate_brown", new RGB(0.2705882352, 0.1960784313, 0.1803921568));
		color_names.put("grey_brown", new RGB(0.2509803921, 0.2274509803, 0.2274509803));
		color_names.put("black_brown", new RGB(0.1294117647, 0.1294117647, 0.1294117647));
		color_names.put("orange_brown", new RGB(0.6509803921, 0.3686274509, 0.1803921568));
		color_names.put("beige_brown", new RGB(0.4745098039, 0.3333333333, 0.2392156862));
		color_names.put("pale_brown", new RGB(0.4588235294, 0.3607843137, 0.2823529411));
		color_names.put("terra_brown", new RGB(0.3058823529, 0.2313725490, 0.1921568627));
		color_names.put("pearl_copper", new RGB(0.4627450980, 0.2352941176, 0.1568627450));
		color_names.put("cream", new RGB(0.9803921568, 0.9568627450, 0.8901960784));
		color_names.put("grey_white", new RGB(0.9058823529, 0.9215686274, 0.8549019607));
		color_names.put("signal_white", new RGB(0.9568627450, 0.9568627450, 0.9568627450));
		color_names.put("signal_black", new RGB(0.1568627450, 0.1568627450, 0.1568627450));
		color_names.put("jet_black", new RGB(0.0392156862, 0.0392156862, 0.0509803921));
		color_names.put("white_aluminium", new RGB(0.6470588235, 0.6470588235, 0.6470588235));
		color_names.put("grey_aluminium", new RGB(0.5607843137, 0.5607843137, 0.5607843137));
		color_names.put("pure_white", new RGB(1.0000000000, 1.0000000000, 1.0000000000));
		color_names.put("graphite_black", new RGB(0.1098039215, 0.1098039215, 0.1098039215));
		color_names.put("traffic_white", new RGB(0.9647058823, 0.9647058823, 0.9647058823));
		color_names.put("traffic_black", new RGB(0.1176470588, 0.1176470588, 0.1176470588));
		color_names.put("papyrus white", new RGB(0.8431372549, 0.8431372549, 0.8431372549));
		color_names.put("pearl_light_grey", new RGB(0.6117647058, 0.6117647058, 0.6117647058));
		color_names.put("pearl_dark_grey", new RGB(0.5098039215, 0.5098039215, 0.5098039215));
		color_names.put("black", new RGB(0.0, 0.0, 0.0));
	}

	public RGB (final double _red, final double _green, final double _blue)
	{
		red = _red;
		green = _green;
		blue = _blue;
	}

	public RGB (final double gray)
	{
		red = gray;
		green = gray;
		blue = gray;
	}

	public static final RGB from_name (final String name)
	{
		return color_names.get(name);
	}

	public final double get_red ()
	{
		return red;	
	}

	public final int get_red_255 ()
	{
		return (int)(red * 255.0);	
	}

	public final double get_green ()
	{
		return green;
	}

	public final int get_green_255 ()
	{
		return (int)(green * 255.0);	
	}

	public final double get_blue ()
	{
		return blue;
	}

	public final int get_blue_255 ()
	{
		return (int)(blue * 255.0);	
	}

	public final RGB negate ()
	{
		return new RGB(
			1.0 - red,
			1.0 - green,
			1.0 - blue);
	}

	public static final double clamp_double (final double d)
	{
		if (d > 1.0)
			return 1.0;
		else if (d < 0.0)
			return 0.0;
		else
			return d;
	}

	public final RGB clamp ()
	{
		return new RGB(
			RGB.clamp_double(red),
			RGB.clamp_double(green),
			RGB.clamp_double(blue));
	}

	public final double luminance ()
	{
		// not implemented yet
		return 0.0;
	}

	public final void gamma (final double g)
	{
		// not implemented yet	
	}

	public boolean equals (final Object obj)
	{
		if (obj == null)
			return false;

		if (obj == this)
			return true;

		if (!(obj instanceof RGB))
			return false;

		RGB color = (RGB) obj;

		if (Double.compare(color.red, this.red) == 0 &&
		    Double.compare(color.green, this.green) == 0 &&
		    Double.compare(color.blue, this.blue) == 0)
			return true;
		else
			return false;
	}

	public final RGB add (final RGB rgb)
	{
		return new RGB(
			red + rgb.get_red(),
			green + rgb.get_green(),
			blue + rgb.get_blue());
	}

	public final RGB subtract (final RGB rgb)
	{
		return new RGB(
			red - rgb.get_red(),
			green - rgb.get_green(),
			blue - rgb.get_blue());
	}

	public final RGB multiply (final RGB rgb)
	{
		return new RGB(
			red * rgb.get_red(),
			green * rgb.get_green(),
			blue * rgb.get_blue());
	}

	public final RGB multiply_scalar (final double scalar)
	{
		return new RGB(
			red * scalar,
			green * scalar,
			blue * scalar);
	}

	public final RGB divide_scalar (final double scalar)
	{
		return new RGB(
			red / scalar,
			green / scalar,
			blue / scalar);
	}

	public final RGB divide (final double scalar)
	{
		return new RGB(
			red / scalar,
			green / scalar,
			blue / scalar);
	}

	public static RGB from_string (String string)
	{
		RGB color = RGB.color_names.get(string);
		if (color != null)
			return color;

		Pattern pattern = Pattern.compile("^\\{(-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+)\\}$");
		Matcher matcher = pattern.matcher(string);

		if (matcher.find())
		{
			String r_string = matcher.group(1);
			double r = 0.0;
			if (r_string != null)
			{
				try
				{
					r = Double.parseDouble(r_string);
				}
				catch (NumberFormatException nfe)
				{
					return null;
				}
			}

			String g_string = matcher.group(2);
			double g = 0.0;
			if (g_string != null)
			{
				try
				{
					g = Double.parseDouble(g_string);
				}
				catch (NumberFormatException nfe)
				{
					return null;
				}
			}

			String b_string = matcher.group(3);
			double b = 0.0;
			if (b_string != null)
			{
				try
				{
					b = Double.parseDouble(b_string);
				}
				catch (NumberFormatException nfe)
				{
					return null;
				}
			}

			return new RGB(r, g, b);
		}
		else
		{
			return null;
		}
	}

	public String toString ()
	{
		return "{" + red + ", " + green + ", " + blue + "}";
	}

	public static final RGB lerp (final RGB one, final RGB two, final double d)
	{
		return one.add(two.subtract(one).multiply_scalar(d));
	}

	public static final RGB lerp2d (
		final RGB one, 
		final RGB two, 
		final RGB three, 
		final RGB four, 
		final double dx, 
		final double dy)
	{
		return lerp(lerp(one, two, dx), lerp(three, four, dx), dy);
	}
}

