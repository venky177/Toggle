package android.venky.com.toggle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

/**
 * Created by milind on 1/31/2016.
 */
public class MySvm {
    svm_problem prob=new svm_problem();
    svm_model model;
    int nofields=2;

    MySvm(Context con)
    {
        Post val;

        List<Post> mydata=FireBase.serverdata;
        int no=mydata.size();
        int nofields=2;
        prob.l=no;
        prob.y = new double[prob.l];
        prob.x = new svm_node[prob.l][nofields];

        for(int i=0;i<no;i++)
        {
            val=mydata.get(i);
            //ret="\n"+val.get("state");
            prob.x[i][0] = new svm_node();
            prob.x[i][0].index = 1;
            prob.x[i][0].value = Double.parseDouble(val.getUid().toString());
            prob.x[i][1] = new svm_node();
            prob.x[i][1].index = 2;
            prob.x[i][1].value = (double)val.getTimestamp().getHours();
            prob.y[i] = Double.parseDouble(val.getState().toString());
            Log.e(val.getUid().toString(),val.getTimestamp().getHours()+" "+val.getState().toString());
        }

    }

    public svm_model setup_train()
    {
        svm_parameter param = new svm_parameter();

        // default values
        // default values
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.LINEAR;
        param.degree = 3;
        param.gamma = 0;
        param.coef0 = 0;
        param.nu = 0.5;
        param.cache_size = 40;
        param.C = 5000;
        param.eps = 1e-3;
        param.p = 0.1;
        param.shrinking = 1;
        param.probability = 0;
        param.nr_weight = 0;
        param.weight_label = new int[0];
        param.weight = new double[0];


        model = svm.svm_train(prob, param);

        String list="";

        for(int i=2;i<12;i++)
        {

            svm_node[] tt= new svm_node[nofields];
            tt[0]= new svm_node();
            tt[0].index=1;
            tt[0].value=1;
            tt[1]= new svm_node();
            tt[1].index=2;
            tt[1].value=i;

            list+="User :"+1+" Time :"+i+" State :"+(int)svm.svm_predict(model,tt)+"\n";

        }
        Log.e("asd",list);
        return model;
    }

    public String get_list()
    {
        String list="";

        for(int i=2;i<12;i++)
        {

            svm_node[] tt= new svm_node[nofields];
            tt[0]= new svm_node();
            tt[0].index=1;
            tt[0].value=1;
            tt[1]= new svm_node();
            tt[1].index=2;
            tt[1].value=i;

            list+="User :"+1+" Time :"+i+" State :"+(int)svm.svm_predict(model,tt)+"\n";

        }
        return list;
    }




}
