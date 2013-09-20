package cz.fg.issuetracking.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Comporator in this version does not support combined verison such as:
 * <p/>
 * b111 > b10
 * rc11 > rc9
 * <p/>
 * and so on.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 */
public class VersionDescriptorTest {

    VersionComparator versionComparator = new VersionComparator();

    static final int FIRST_BIGGER = 1;
    static final int FIRST_SMALLER = -1;
    static final int BOTH_EQUALS = 0;

    @Test
    public void testSnapshots() throws Exception {

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0-SNAPSHOT"),
                        new VersionDescriptor("1.0.0")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0"),
                        new VersionDescriptor("1.0.0-SNAPSHOT")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0-SNAPSHOT"),
                        new VersionDescriptor("1.0.1-SNAPSHOT")
                )
        );

    }

    @Test
    public void testCutVersions() throws Exception {

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1"),
                        new VersionDescriptor("1.0")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.1"),
                        new VersionDescriptor("1.1.0.0")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.1-SNAPSHOT"),
                        new VersionDescriptor("1.1.0.0-SNAPSHOT")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.0"),
                        new VersionDescriptor("1")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.1.0.0"),
                        new VersionDescriptor("1.1")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.1.0.0-SNAPSHOT"),
                        new VersionDescriptor("1.1-SNAPSHOT")
                )
        );

    }

    @Test
    public void testCombined() throws Exception {

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha"),
                        new VersionDescriptor("1.0-beta")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha"),
                        new VersionDescriptor("1.0-alpha")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("1.0-beta"),
                        new VersionDescriptor("1.0-alpha")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("1.0"),
                        new VersionDescriptor("1.0-alpha")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("1.0"),
                        new VersionDescriptor("1.0-rc")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.0-RC"),
                        new VersionDescriptor("1.0-rc")
                )
        );

    }

    @Test
    public void testCombinedDifficult() throws Exception {

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-1"),
                        new VersionDescriptor("1.0-alpha-2")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-20"),
                        new VersionDescriptor("1.0-alpha-20")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-20"),
                        new VersionDescriptor("1.0-alpha-2")
                )
        );

    }

    @Test
    public void testCombinedDifficult2() throws Exception {

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("1.1-beta-2"),
                        new VersionDescriptor("1.0")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-1"),
                        new VersionDescriptor("1.0")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("1.1-alpha-20"),
                        new VersionDescriptor("1.0-beta")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1.1-alpha-3"),
                        new VersionDescriptor("1.3")
                )
        );

    }

    @Test
    public void testCompareEasyNumberVersions() throws Exception {

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1"),
                        new VersionDescriptor("2")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("10"),
                        new VersionDescriptor("2")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("5"),
                        new VersionDescriptor("5")
                )
        );

    }

    @Test
    public void testCompareEasyAlfanumericVersions() throws Exception {

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("a"),
                        new VersionDescriptor("b")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("c"),
                        new VersionDescriptor("a")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("a"),
                        new VersionDescriptor("a")
                )
        );

    }

    @Test
    public void testCompareComplicatedNumberVersions() throws Exception {

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0"),
                        new VersionDescriptor("2")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("10"),
                        new VersionDescriptor("2.0.0")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("5.0.0"),
                        new VersionDescriptor("5.0.0")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("5.0.1"),
                        new VersionDescriptor("5.0.0")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("5.0.0"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("5.0.5"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("5.1.10"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("5"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("5.1.1"),
                        new VersionDescriptor("5.1")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("5.0.11"),
                        new VersionDescriptor("5.0.3")
                )
        );

    }

    @Test
    public void testCompareComplicatedAlfanumericVersions() throws Exception {

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("a.a.a"),
                        new VersionDescriptor("b")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("f"),
                        new VersionDescriptor("b.a.a")
                )
        );

        assertEquals(
                BOTH_EQUALS,
                versionComparator.compare(
                        new VersionDescriptor("e.a.a"),
                        new VersionDescriptor("e.a.a")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("e.a.b"),
                        new VersionDescriptor("e.a.a")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("e.a.a"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("e.a.b"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                FIRST_BIGGER,
                versionComparator.compare(
                        new VersionDescriptor("e.b.f"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("e"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                FIRST_SMALLER,
                versionComparator.compare(
                        new VersionDescriptor("e.b.b"),
                        new VersionDescriptor("e.b")
                )
        );

    }

}
