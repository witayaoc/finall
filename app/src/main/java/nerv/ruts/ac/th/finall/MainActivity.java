package nerv.ruts.ac.th.finall;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

        public FirebaseDatabase firebaseDatabase;
        public DatabaseReference LED,refer1;
        private static  final String TAG = "LED";
        public Button bt;
        public String data;
        public TextView textView;
        public  Integer value,value_refer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase  = FirebaseDatabase.getInstance();
        LED = firebaseDatabase.getReference("led");
        refer1 = firebaseDatabase.getReference();
        bt =(Button)findViewById(R.id.bt1);
        textView =(TextView)findViewById(R.id.t);

        refer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                data = String.valueOf(map.get("led"));
                textView.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        LED.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(Integer.class);
                if (value==1){
                    bt.setText("LED ON");
                    value_refer = 0;
                }else {
                    bt.setText("LED OFF");
                    value_refer = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
Log.w("Error",databaseError.toException());
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LED.setValue(value_refer);
            }
        });
    }
}
