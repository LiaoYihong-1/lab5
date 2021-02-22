package CSV;

import Collection.Person;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * write in format csv
 */
public class CSVWriter {

    /**
     * write a linkedHashSet<Person> to a file in csv format
     * @param linkedHashSet
     * @param path
     * @throws IOException
     */
    public void WriterToFile(LinkedHashSet<Person> linkedHashSet,String path) throws IOException {
        File file=new File(path);
        String firstline="id,name,haircolor,eyecolor,height,location,x,y,z,creationdate,birthday\n";
        BufferedOutputStream BOS=new BufferedOutputStream(new FileOutputStream(file));
        BOS.write(firstline.getBytes());
        Iterator<Person> iterator=linkedHashSet.iterator();
        while (iterator.hasNext()){
            BOS.write(iterator.next().toString().getBytes());
        }
        BOS.close();
    }

    /**
     * write a person to file in csv format
     * @param person
     * @param path
     * @throws IOException
     */
    public void WriteOnePerson(Person person,String path) throws IOException{
        File file=new File(path);
        BufferedOutputStream BOS=new BufferedOutputStream(new FileOutputStream(file));
        String firstline="id,name,haircolor,eyecolor,height,location,x,y,z,creationdate,birthday\n";
        BOS.write(firstline.getBytes());
        BOS.write(person.toString().getBytes());
        BOS.close();
    }
}