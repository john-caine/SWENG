package player;

import static org.junit.Assert.*;

import org.junit.Test;

public class VideoPlayerHandlerTest {

	@Test
	public void fileTest(){
		int xStart = 200;
		int yStart = 200;
		String String = "C:/Users/ProBookMac/workspace/VideoPlayer/src/avengers-featurehp.mp4";
		
		VideoPlayerHandler VPH = new VideoPlayerHandler(String, xStart, yStart);
		assertNotNull(VPH);
	}
	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
