package Command;

/**
 * command RemoveGreater
 */
public class RemoveGreater extends AbstractCommand {
    public RemoveGreater(){
        this.name="RemoveGreater";
        this.help="remove all elements greater than the specified one from the collection";
    }

    /**
     * remove all the elements,whose id bigger than input.(Не знаю надо применять какое значение, у меня ещё вариант с hashcode)
     * {@link CommandManager#executeRemoveGreater(Integer)}
     * @param commandManager
     * @param args
     * @throws ParaInapproException
     */
    public void execute(CommandManager commandManager,String [] args) throws ParaInapproException{
        if(args.length>2){
            throw new ParaInapproException("this command only and must accept one number parameter\n");
        }else {
            commandManager.executeRemoveGreater(Integer.valueOf(args[1]));
        }
    }
}
