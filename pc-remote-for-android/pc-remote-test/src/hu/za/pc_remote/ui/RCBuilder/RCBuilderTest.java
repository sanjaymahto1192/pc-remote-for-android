package hu.za.pc_remote.ui.RCBuilder;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/11/11
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RCBuilderTest extends ActivityInstrumentationTestCase2<RCBuilder> {

    private RCBuilder mActivity;

    public RCBuilderTest() {
        super("hu.za.pc_remote.ui.RCBuilder", RCBuilder.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
    }

/*    public void testPreconditions() {
        mActivity.getViews();

        try {
            FileReader fr = new FileReader(Environment.getDataDirectory().getPath() + File.pathSeparatorChar + "pcremote" + File.pathSeparatorChar + "file");
            BufferedReader br = new BufferedReader(fr);
            assertTrue(br.readLine().startsWith("HelloWorld"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }*/
}
