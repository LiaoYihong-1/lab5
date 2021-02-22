package Lab;

import java.io.*;
import java.util.Iterator;
import Collection.*;
import Command.*;
import Tools.Tools;

public class Lab {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String args []) throws IOException {
        CommandManager commandManager=new CommandManager();
        new CollectionsofPerson().doInitialization();
        while(true) {
            boolean exist=false;//make sure command exists
            AbstractCommand abstractCommand;
            Iterator<AbstractCommand> iterator = commandManager.getCommands().iterator();
            System.out.print("input your command:\n");
            String[] command = Tools.Input().split(" ");
            try {
                while (iterator.hasNext()) {
                    if ((abstractCommand = iterator.next()).getName().equalsIgnoreCase(command[0])) {
                        abstractCommand.execute(commandManager, command);
                        new History().getHistory().add(abstractCommand.getName()+"\n");
                        exist=true;//set true when command exists
                    }
                }
                if(exist==false){
                    throw new NoSuchCommandException("No such command, please enter another one\n");
                }
            }catch (NoSuchCommandException e){
                System.out.print(e.getMessage());
            }catch (ParaInapproException e){
                System.out.print(e.getMessage());
                System.out.print("Input another command\n");
            }catch (IOException e){
                System.out.print(e.getMessage()+"\n");
            }catch (NullException e){
                System.out.print(e.getMessage()+"\n");
            }catch (NumberFormatException e){
                System.out.print("please input a number instead of word\n");
            }catch (NotSuchColorException e){
                System.out.print(e.getMessage());
            }catch (ValueTooSmallException e){
                System.out.print(e.getMessage());
            }catch (ValueTooBigException e){
                System.out.print(e.getMessage());
            }
        }
    }
}
