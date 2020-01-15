package controller.command;

public class ExitTest {

    private ViewFake view = new ViewFake();

//    @Test
//    public void testCanProcess_ExitCommand() {
//        // given
//        Command exit = new Exit(view);
//
//        // when
//        boolean canProcess = exit.canProcess("exit");
//
//        // then
//        assertTrue(canProcess);
//    }
//
//    @Test
//    public void testCanProcess_NotExitCommand() {
//        // given
//        Command exit = new Exit(view);
//
//        // when
//        boolean canProcess = exit.canProcess("notExit");
//
//        // then
//        assertFalse(canProcess);
//    }
//
//    @Test
//    public void testProcess() {
//        // given
//        Command exit = new Exit(view);
//
//        // when
//        try {
//            exit.process("exit");
//            fail("Expected ExitException");
//        } catch (ExitException e) {
//            // do nothing
//        }
//
//        // then
//        assertEquals("See you soon!\n", view.getContent());
//    }
}