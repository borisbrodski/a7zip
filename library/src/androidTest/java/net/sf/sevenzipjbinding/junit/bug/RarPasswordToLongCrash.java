package net.sf.sevenzipjbinding.junit.bug;

import net.sf.sevenzipjbinding.ArchiveFormat;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.junit.JUnitNativeTestBase;

import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;

public class RarPasswordToLongCrash extends JUnitNativeTestBase {
    private static final String PASSWORD = "123456789012345678901234567890";

    @Test
    public void openArchiveWithCorrectPassword() throws Throwable {
        RandomAccessFile randomAccessFile = null;
        IInArchive inArchive = null;

        Throwable throwable = null;
        try {
            randomAccessFile = new RandomAccessFile(new File(getDataDir(), "bug/RAR archive Crash (over 30 char password).rar"), "r");
            inArchive = SevenZip.openInArchive(ArchiveFormat.RAR, new RandomAccessFileInStream(randomAccessFile),
                    PASSWORD);
        } catch (Throwable e) {
            throwable = e;
        }
        if (inArchive != null) {
            try {
                inArchive.close();
            } catch (Throwable e) {
                if (throwable == null) {
                    throwable = e;
                }
            }
        }
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (Throwable e) {
                if (throwable == null) {
                    throwable = e;
                }
            }
        }
        if (throwable != null) {
            throw throwable;
        }
    }
}
