package uk.ac.ebi.interpro.scan.business.postprocessing.panther;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.interpro.scan.business.postprocessing.pirsf.BlastPostProcessor;
import uk.ac.ebi.interpro.scan.model.raw.PIRSFHmmer2RawMatch;
import uk.ac.ebi.interpro.scan.model.raw.PantherRawMatch;
import uk.ac.ebi.interpro.scan.model.raw.RawMatch;
import uk.ac.ebi.interpro.scan.model.raw.RawProtein;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class PantherPostProcessorTest {

    private PantherPostProcessor processor;

    @Before
    public void init() {
        processor = new PantherPostProcessor(-11.0d);
    }

    @Test
    public void testPantherPostProcessor() {
        //Build a set of raw protein matches to process
        Set<RawProtein<PantherRawMatch>> rawMatches = new HashSet<RawProtein<PantherRawMatch>>();
        //Build a raw protein with a set of raw matches
        RawProtein<PantherRawMatch> rawProtein = new RawProtein<PantherRawMatch>("1");
        //Build raw matches and add them to the raw protein
        PantherRawMatch rawMatch1 = getDefaultPantherRawMatchObj("1");
        rawMatch1.setEvalue(-10.8d);
        rawProtein.addMatch(rawMatch1);
        //
        PantherRawMatch rawMatch3 = getDefaultPantherRawMatchObj("2");
        rawMatch3.setEvalue(-11.0d);
        rawProtein.addMatch(rawMatch3);
        //
        PantherRawMatch rawMatch2 = getDefaultPantherRawMatchObj("3");
        rawMatch2.setEvalue(-11.1d);
        rawProtein.addMatch(rawMatch2);
        //
        rawMatches.add(rawProtein);
        assertEquals("Actual match size is different to the expected match size!", 3, rawProtein.getMatches().size());
        //Filter raw matches
        Set<RawProtein<PantherRawMatch>> filteredMatches = processor.process(rawMatches);
        assertEquals(1, filteredMatches.size());
        for (RawProtein<PantherRawMatch> item : filteredMatches) {
            assertEquals("Actual match size is different to the expected match size!", 2, item.getMatches().size());
        }
    }

    private PantherRawMatch getDefaultPantherRawMatchObj(String sequenceIdentifier) {
        return new PantherRawMatch(sequenceIdentifier, "model", "signatureLibraryRelease", 0, 0, 0.0d, 0.0d, "familyName");
    }
}