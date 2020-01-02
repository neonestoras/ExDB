package Papers;

import org.json.simple.parser.ParseException;

// TNFAVivo table papers
public class TNFAVivo extends  Paper{
    public TNFAVivo(int id, String link, String figure, String author, String morphotype, String liveORfixed, String fixationmethod, String initialAFinoculum, String afumigatusstrain, String infectionmodel, String strain, String age, String treatment, String infectionroute, String sample, String sampleVolume, String sampleSize, String tUnits, String times) throws ParseException {
        super(id, link, figure, author, morphotype, liveORfixed, fixationmethod, initialAFinoculum, afumigatusstrain, infectionmodel, strain, age, treatment, infectionroute, sample, sampleVolume, sampleSize, tUnits, times);
    }
}
