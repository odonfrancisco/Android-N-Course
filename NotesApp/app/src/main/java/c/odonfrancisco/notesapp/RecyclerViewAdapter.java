package c.odonfrancisco.notesapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<String> notesList;
    private Activity activity;

    // Contstructor
    public RecyclerViewAdapter(Context mContext, Activity activity, ArrayList<String> notesList){
        this.mContext = mContext;
        this.notesList = notesList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_list_item_view, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.noteText.setText(notesList.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, notesList.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, EditNoteText.class);
                intent.putExtra("noteID", i);

                activity.startActivityForResult(intent, 1);
            }
        });

        viewHolder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteAlertDialog(i);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() { return notesList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteText;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            noteText = itemView.findViewById(R.id.noteText);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    private void deleteAlertDialog(final int itemIndex){
        new AlertDialog.Builder(this.mContext)
                .setIcon(android.R.drawable.star_on)
                .setTitle("Are you sure you want to delete this note?")
                .setMessage("Deleting this is undoable")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote(itemIndex);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteNote(int index){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("c.odonfrancisco.notesapp", Context.MODE_PRIVATE);

        Set<String> notesSet = sharedPreferences.getStringSet("notes", new HashSet<String>());

        notesSet.remove(notesList.get(index));

        sharedPreferences.edit().putStringSet("notes", notesSet).apply();

        activity.recreate();
    }
}
