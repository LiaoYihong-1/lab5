package Command;

import CSV.CSVWriter;
import Collection.*;
import Tools.Tools;
import java.io.*;
import java.util.*;

/**
 * command manager
 */
public class CommandManager {
    public CommandManager(){
        commands.add(new Add());
        commands.add(new Addifmin());
        commands.add(new Average());
        commands.add(new Clear());
        commands.add(new ExecuteScript());
        commands.add(new Exit());
        commands.add(new Help());
        commands.add(new History());
        commands.add(new Info());
        commands.add(new Print());
        commands.add(new RemoveById());
        commands.add(new RemoveEyeColor());
        commands.add(new RemoveGreater());
        commands.add(new Save());
        commands.add(new Show());
        commands.add(new UpdateID());
    }

    private static LinkedHashSet<AbstractCommand> commands=new LinkedHashSet<>();
    private boolean findid=false;

    /**
     * get static LinkedHashSet<AbstrcteCommand> commands
     * @return LinkedHashSet
     */
    public LinkedHashSet getCommands() {
        return this.commands;
    }

    /**
     * use Iterator to read LinkedHashSet<AbstractCommand> commands,and print all helps
     */
    public void executeHelp() {
        Iterator<AbstractCommand> iterator = commands.iterator();
        while (iterator.hasNext()) {
            AbstractCommand A = iterator.next();
            System.out.print(A.getName() + ":" + A.getHelp() + "\n");
        }
    }

    /**
     * user set and add a new object with the help of static method {@link Person#PeopleCreate()}
     * @param person
     * @throws ValueTooBigException
     * @throws ValueTooSmallException
     * @throws NullException
     */
    public void executeAdd(Person person) throws ValueTooBigException,ValueTooSmallException,NullException{
        new CollectionsofPerson().doInitialization();
        new CollectionsofPerson().getPeople().add(person);
    }

    /**
     * use method {@link CommandManager#findMin()} to find the min person in the collections.
     * If user set a person smaller than him, add the new person
     * @param p
     */
    public void executeAddifmin(Person p) {
        if(p.hashCode()<findMin().hashCode()){
            executeAdd(p);
        }
    }

    /**
     * find the min object(here i use hashcode,because if use id,the latest object will be the biggest)
     *
     * @return Person
     */
    public Person findMin(){
        LinkedHashSet<Person> people=new CollectionsofPerson().getPeople();
        Iterator<Person> iterator= people.iterator();
        Person p=iterator.next();
        Person person;
        while(iterator.hasNext()){
            if(p.hashCode()>(person=iterator.next()).hashCode()){
                p=person;
            }
        }
        return p;
    }

    /**
     * print the average of height
     */
    public void executeAverage() {
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        if(iterator.hasNext()) {
            Integer Total = 0;
            while (iterator.hasNext()) {
                Total = Total + iterator.next().getHeight();
            }
            System.out.print(Total / (new CollectionsofPerson().getPeople().size()) + "\n");
        }else{
            throw new NullException("collections of people still empty\n");
        }
    }

    /**
     * clear all elements in collections
     */
    public void executeClear() {
        new CollectionsofPerson().getPeople().clear();
    }

    /**
     * exit the program
     */
    public void executeExit() {
        System.exit(2);
    }

    /**
     * print the information of collection(type,amount of elements,when it is created)
     */
    public void executeInfo() {
        if(!CollectionsofPerson.Initialization){
            throw new NotInitializationException("collections was initialized");
        }else {
            System.out.print("the date of initialization is "+new CollectionsofPerson().getInitializationTime()+"\n");
        }
        System.out.print("the amount of elements is "+ new CollectionsofPerson().getPeople().size()+"\n");
        System.out.print("the type of collection is "+ new CollectionsofPerson().getPeople().getClass() +"\n");
    }

    /**
     * print all the elements
     * When collections is empty,throw NullException
     * {@link CommandManager#findByLocationHash(Integer)}
     * {@link CommandManager#getValues()}
     * @throws NullException
     */
    public void executePrint() throws NullException{
        if(new CollectionsofPerson().getPeople().size()==0){
            throw new NullException("collections still empty\n");
        }
        Integer [] list=getValues();
        int [] values=new int[list.length];
        for(int i=0;i<values.length;i++){
            values[i]=list[i];
        }
        Arrays.sort(values);
        for(int i=0;i<values.length;i++){
            findByLocationHash(values[i]);
        }
    }

    /**
     * get the location of all people
     * @return
     */
    private LinkedHashSet<Location> getLocations(){
        LinkedHashSet<Location> linkedHashSet=new LinkedHashSet<>();
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        while(iterator.hasNext()){
            linkedHashSet.add(iterator.next().getLocation());
        }
        return linkedHashSet;
    }

    /**
     * return the values(hashcode) of all locations
     * {@link CommandManager#getLocations()}
     * @return Integer[]
     */
    private Integer[] getValues(){
        LinkedHashSet<Integer> arrayList=new LinkedHashSet<>();
        Iterator<Location> iterator=getLocations().iterator();
        while(iterator.hasNext()){
            arrayList.add(iterator.next().hashCode());
        }
        Integer []list=arrayList.toArray(new Integer[arrayList.size()]);
        return list;
    }

    /**
     * print the location with specified hashcode
     * @param value
     */
    private void findByLocationHash(Integer value){
        Location location;
        Location print;
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        out:while (iterator.hasNext()){
            if((location=iterator.next().getLocation()).hashCode()==value){
                print=location;
                System.out.print(print.toString()+"\n");
                break out;
            }
        }
    }

    public void executeRemoveById(Integer id) {
        Person p=findByid(id);
        if(findid==false){
            throw new ParaInapproException("no such a person with this id\n");
        }
        findid=false;
        new CollectionsofPerson().getPeople().remove(p);
    }

    /**
     * return person with specified id
     * @param id
     * @return Person
     */
    private Person findByid(Integer id){
        Person p=null;
        Person m;
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        out:while(iterator.hasNext()){
            if((m=iterator.next()).getId()==id){
                p=m;
                findid=true;
                break out;
            }
        }
        return p;
    }

    /**
     * remove person with specified eye color
     * {@link CommandManager#findbyEye(String)}
     * @param eye
     */
    public void executeRemoveEyeColor(String eye){
        for(Person p:findbyEye(eye)) {
            new CollectionsofPerson().getPeople().remove(p);
        }
    }

    /**
     * find person with Specified eye color
     * @param eye
     * @return
     */
    private LinkedHashSet<Person> findbyEye(String eye){
        LinkedHashSet<Person> linkedHashSet=new LinkedHashSet<>();
        Person A;
        EyeColor eyeColor=EyeColor.valueOf(eye);
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        while(iterator.hasNext()){
            if((A=iterator.next()).getEyeColor()==eyeColor){
                linkedHashSet.add(A);
            }
        }
        return linkedHashSet;
    }

    /**
     * Remove all the people,whose if bigger than specified
     * {@link CommandManager#findByid(Integer)}
     * {@link CommandManager#findGreater(Integer)}
     * @param in
     */
    public void executeRemoveGreater(Integer in) {
        Person B=findByid(Integer.valueOf(in));
        if(B==null){
            System.out.print("No element is available");
        }else {
            for(Person p:findGreater(in)){
                new CollectionsofPerson().getPeople().remove(p);
            }
        }
    }

    /**
     * find all pepole,whose hashcode bigger than specified
     * @param id
     * @return LinkedHashSet<Person>
     */
    private LinkedHashSet<Person> findGreater(Integer id){
        LinkedHashSet<Person> linkedHashSet=new LinkedHashSet<>();
        Person A;
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        while(iterator.hasNext()){
            if((A=iterator.next()).getId()>id){
                linkedHashSet.add(A);
            }
        }
        return linkedHashSet;
    }

    /**
     * write static LinkedHashSet in format csv
     * {@link CSVWriter#WriterToFile(LinkedHashSet, String)}
     * @throws IOException
     */
    public void executeSave() throws IOException {
        LinkedHashSet<Person> linkedHashSet=new CollectionsofPerson().getPeople();
        new CSVWriter().WriterToFile(linkedHashSet,"src\\Person.csv");
    }

    /**
     * print all the elements
     */
    public void executeShow() {
        if(new CollectionsofPerson().getPeople().size()==0){
            throw new NullException("collections of people still empty\n");
        }
        new Tools().PrintPersonSet(new CollectionsofPerson().getPeople());
    }

    /**
     * reset element with specified id.
     * @param in
     */
    public void executeUpdateID(String in) {
        Integer id=Integer.valueOf(in);
        Person p;
        Iterator<Person> iterator=new CollectionsofPerson().getPeople().iterator();
        out:while(iterator.hasNext()){
            if((p=iterator.next()).getId()==id){
                new CollectionsofPerson().getPeople().remove(p);
                Person insert=Person.PeopleCreate();
                insert.changeId(id);
                Person.balaceicode();
                new CollectionsofPerson().getPeople().add(insert);
                break out;
            }
        }
    }

    /**
     * print the last 7 commands
     */
    public void executeHistory() {
        int size=new History().getHistory().size();
        Iterator<String> iterator=new History().getHistory().iterator();
        if(new History().getHistory().size()<=7){
            while(iterator.hasNext()){
                System.out.print(iterator.next());
            }
        }else{
            while(size>7){
                iterator.next();
                size--;
            }
            while(iterator.hasNext()){
                System.out.print(iterator.next());
            }
        }
    }

    /**
     * Execute the command written in file
     * {@link CommandManager#findCommand(String)}
     * @param name
     * @throws IOException
     * @throws ParaInapproException
     */
    public void executeExecuteScript(String name,CommandManager commandManager) throws IOException,ParaInapproException{
        FileReader f=new FileReader(new File("src\\"+name));
        BufferedReader bufferedReader=new BufferedReader(f);
        String commandtext="";
        while((commandtext=bufferedReader.readLine())!=null){
            String []split=commandtext.split(" ");
            AbstractCommand command=findCommand(split[0]);
            if(command!=null) {
                command.execute(commandManager,split);
                new History().getHistory().add(command.getName()+"\n");
            }
        }
        bufferedReader.close();
    }

    /**
     * find the command with specified name
     * @param name
     * @return
     */
    public AbstractCommand findCommand(String name){
        AbstractCommand A=null;
        AbstractCommand B;
        Iterator<AbstractCommand> iterator=commands.iterator();
        while(iterator.hasNext()){
            if((B=iterator.next()).getName().equalsIgnoreCase(name)) {
                A = B;
            }
        }
        return A;
    }
}
