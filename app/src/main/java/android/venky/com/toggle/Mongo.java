package android.venky.com.toggle;

import android.util.Log;
import android.widget.Toast;

import com.mongodb.*;

import org.apache.http.HttpResponse;

import libsvm.svm;
import libsvm.svm_node;
import libsvm.svm_problem;

/**
 * Created by milind on 1/16/2016.
 */
public class Mongo {
    public String ret="";
    MongoClient c1=null;
    svm_problem prob=new svm_problem();
    public Mongo(final String ip,final int port)
    {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Your code goes here
                    Log.e("lOl","dsf");
                    Log.e(ip.trim(),"lol"+port+"");
                    c1=new MongoClient(ip.trim(),port);
                    DB db=c1.getDB("test");


                    DBCollection coll=db.getCollection("pi");
                    //BasicDBObject doc=new BasicDBObject("roll","2").append("name", new BasicDBObject("fname","fff").append("lname", "kkk")).append("DMSA", 60);
//coll.insert(doc);

                    DBCursor cur=coll.find(new BasicDBObject());
                    //out.print("\n");
                    DBObject val;

                    while(cur.hasNext())
                    {
                        val=cur.next();
                        ret="\n"+val.get("state");
                    }
                    Log.e("kslakd",ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String getdata()
    {return ret;}

    public svm_problem getproblem()
    {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Your code goes here
                    DB db=c1.getDB("test");
                    DBCollection coll=db.getCollection("train");
                    DBCursor cur=coll.find(new BasicDBObject());
                    DBObject val;
                    int no=cur.count();

                    int nofields=2;
                    prob.l=no;
                    prob.y = new double[prob.l];
                    prob.x = new svm_node[prob.l][nofields];
                    int i=0;
                    while(cur.hasNext())
                    {
                        val=cur.next();
                        ret="\n"+val.get("state");
                        prob.x[i][0] = new svm_node();
                        prob.x[i][0].index = 1;
                        prob.x[i][0].value = Double.parseDouble(val.get("user").toString());
                        prob.x[i][1] = new svm_node();
                        prob.x[i][1].index = 2;
                        prob.x[i][1].value = Double.parseDouble(val.get("time").toString());
                        prob.y[i] = Double.parseDouble(val.get("state").toString());
                        i++;
                        Log.e(val.get("user").toString(),val.get("time").toString()+" "+val.get("state").toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return prob;
    }


}
