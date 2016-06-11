package com.example.macbook.getref;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by macbook on 13.04.16.
 */
public class ScrollingMovies extends Activity {
    DBMovies mDBConnector;
    Context mContext;
    ListView mListView;
    MovieListAdapter mmlAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_view);


        mContext=this;
        mDBConnector=new DBMovies(this);
        mListView=(ListView)findViewById(R.id.movieScroll);

        mmlAdapter=new MovieListAdapter(mContext,mDBConnector.selectAllMovies());
        mListView.setAdapter(mmlAdapter);
        registerForContextMenu(mListView);
        ParseJsonTask p = new ParseJsonTask();
        p.execute(getApplicationContext());
    }
    public void updateList(ArrayList<String[]> newList){
        mmlAdapter.update(newList);
        mmlAdapter.notifyDataSetChanged();
    }


    private class ParseJsonTask extends AsyncTask<Context, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Context... params) {
            if(!isDeviceOnline(params[0])){
                return false;
            }
            try{
                ParseJson parse = new ParseJson();
                String json = parse.getJson();
                List<Movie> moviesList = parse.getMoviesFromJson(json);
                Collections.sort(moviesList);
                ArrayList<String[]> strL = new ArrayList<String[]>();
                mDBConnector.deleteAll();
                for(Movie m : moviesList){
                   mDBConnector.insert (m.getName(), m.getDescription(), m.getActors(), m.getPicture());
                }

                return true;
            }
            catch (IOException e){
                System.err.println("JSON ERROR - "+e.toString());
            }
            return null;
        }
        private boolean isDeviceOnline(Context context){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        @Override
        protected void onPostExecute(Boolean param){
            super.onPostExecute(param);
            if(param==null){
                Toast.makeText(getApplicationContext(),"DOWNLOAD ERROR",Toast.LENGTH_SHORT).show();
            }
            else if(param){
                mmlAdapter.notifyDataSetChanged();
            }
            else if(!param){
                Toast.makeText(getApplicationContext(),"NO INTERNET ERROR",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MovieListAdapter extends BaseAdapter{
        LayoutInflater mInflater;
        ArrayList<String[]> mArrayMovie;

        public MovieListAdapter(Context context, ArrayList<String[]> movies){
            mInflater = LayoutInflater.from(context);
            mArrayMovie = movies;
        }

        public void update(ArrayList<String[]> newList){
            mArrayMovie=newList;
        }

        @Override
        public int getCount() {
            return mArrayMovie.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            String[] mMovie = mArrayMovie.get(position);
            if(mMovie!=null)
                return Long.parseLong(mMovie[0]);
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = mInflater.inflate(R.layout.movie_list_child, null);

            TextView vMovie=(TextView)convertView.findViewById(R.id.movieListItem);

            if((mArrayMovie.get(position)!=null)&&(vMovie!=null)) {

                vMovie.setText(mArrayMovie.get(position)[1]);
                vMovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ScrollingMovies.this, MoviePage.class);

                        intent.putExtra("title", mArrayMovie.get(position)[1]);
                        intent.putExtra("description", mArrayMovie.get(position)[2]);
                        intent.putExtra("actors", mArrayMovie.get(position)[3]);
                        intent.putExtra("picture", mArrayMovie.get(position)[4]);
                        intent.putExtra("id", mArrayMovie.get(position)[0]);
                        startActivity(intent);
                    }
                });
            }

            return vMovie;
        }

    }

}
