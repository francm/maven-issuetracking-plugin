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

    @Test
    public void testSnapshots() throws Exception {

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0-SNAPSHOT"),
                        new VersionDescriptor("1.0.0")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0"),
                        new VersionDescriptor("1.0.0-SNAPSHOT")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0-SNAPSHOT"),
                        new VersionDescriptor("1.0.1-SNAPSHOT")
                )
        );

    }

    @Test
    public void testCutVersions() throws Exception {

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1"),
                        new VersionDescriptor("1.0")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.1"),
                        new VersionDescriptor("1.1.0.0")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.1-SNAPSHOT"),
                        new VersionDescriptor("1.1.0.0-SNAPSHOT")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.0"),
                        new VersionDescriptor("1")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.1.0.0"),
                        new VersionDescriptor("1.1")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.1.0.0-SNAPSHOT"),
                        new VersionDescriptor("1.1-SNAPSHOT")
                )
        );

    }

    @Test
    public void testCombined() throws Exception {

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha"),
                        new VersionDescriptor("1.0-beta")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha"),
                        new VersionDescriptor("1.0-alpha")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("1.0-beta"),
                        new VersionDescriptor("1.0-alpha")
                )
        );

    }

    @Test
    public void testCombinedDifficult() throws Exception {

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-1"),
                        new VersionDescriptor("1.0-alpha-2")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-20"),
                        new VersionDescriptor("1.0-alpha-20")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-20"),
                        new VersionDescriptor("1.0-alpha-2")
                )
        );

    }

    @Test
    public void testCombinedDifficult2() throws Exception {

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("1.1-beta-2"),
                        new VersionDescriptor("1.0")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1.0-alpha-1"),
                        new VersionDescriptor("1.0")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("1.1-alpha-20"),
                        new VersionDescriptor("1.0-beta")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1.1-alpha-3"),
                        new VersionDescriptor("1.3")
                )
        );

    }

    @Test
    public void testCompareEasyNumberVersions() throws Exception {

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1"),
                        new VersionDescriptor("2")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("10"),
                        new VersionDescriptor("2")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("5"),
                        new VersionDescriptor("5")
                )
        );

    }

    @Test
    public void testCompareEasyAlfanumericVersions() throws Exception {

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("a"),
                        new VersionDescriptor("b")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("c"),
                        new VersionDescriptor("a")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("a"),
                        new VersionDescriptor("a")
                )
        );

    }

    @Test
    public void testCompareComplicatedNumberVersions() throws Exception {

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("1.0.0"),
                        new VersionDescriptor("2")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("10"),
                        new VersionDescriptor("2.0.0")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("5.0.0"),
                        new VersionDescriptor("5.0.0")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("5.0.1"),
                        new VersionDescriptor("5.0.0")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("5.0.0"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("5.0.5"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("5.1.10"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("5"),
                        new VersionDescriptor("5.1.0")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("5.1.1"),
                        new VersionDescriptor("5.1")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("5.0.11"),
                        new VersionDescriptor("5.0.3")
                )
        );

    }

    @Test
    public void testCompareComplicatedAlfanumericVersions() throws Exception {

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("a.a.a"),
                        new VersionDescriptor("b")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("f"),
                        new VersionDescriptor("b.a.a")
                )
        );

        assertEquals(
                0,
                versionComparator.compare(
                        new VersionDescriptor("e.a.a"),
                        new VersionDescriptor("e.a.a")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("e.a.b"),
                        new VersionDescriptor("e.a.a")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("e.a.a"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("e.a.b"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                1,
                versionComparator.compare(
                        new VersionDescriptor("e.b.f"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("e"),
                        new VersionDescriptor("e.b.a")
                )
        );

        assertEquals(
                -1,
                versionComparator.compare(
                        new VersionDescriptor("e.b.b"),
                        new VersionDescriptor("e.b")
                )
        );

    }

}
