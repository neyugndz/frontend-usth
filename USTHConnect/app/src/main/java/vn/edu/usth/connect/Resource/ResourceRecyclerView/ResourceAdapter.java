package vn.edu.usth.connect.Resource.ResourceRecyclerView;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.Models.Moodle.Resource;
import vn.edu.usth.connect.R;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {


    private List<Resource> resourceList;

    public ResourceAdapter(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public void setResources(List<Resource> resources) {
        this.resourceList = resources;
        notifyDataSetChanged();
    }

    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        Log.d("ResourceAdapter", "Binding Resource: " + resource.getResourceName() + ", URL: " + resource.getResourceUrl());

        holder.resourceName.setText(resource.getResourceName());
        holder.resourceUrl.setText(resource.getResourceUrl());
        holder.resourceIcon.setImageResource(R.drawable.file);

        // Set an OnClickListener to handle item click
        holder.itemView.setOnClickListener(v -> {
            // Start downloading the file
            downloadFile(holder.itemView.getContext(), resource.getResourceUrl(), resource.getResourceName());
        });
    }

    private void downloadFile(Context context, String fileUrl, String fileName) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        if (downloadManager != null) {
            Uri fileUri = Uri.parse(fileUrl);
            DownloadManager.Request request = new DownloadManager.Request(fileUri);

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("Downloading " + fileName);
            request.setDescription("Downloading file...");

            // Enqueue the request and get the download ID
            long downloadId = downloadManager.enqueue(request);

            // Use a DownloadManager query to get the file URI after download completion
            new Thread(() -> {
                boolean isDownloadFinished = false;
                while (!isDownloadFinished) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    try (Cursor cursor = downloadManager.query(query)) {
                        if (cursor != null && cursor.moveToFirst()) {
                            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                String localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                if (localUri != null) {
                                    Uri fileUriAfterDownload = Uri.parse(localUri);
                                    openFile(context, fileUriAfterDownload);
                                }
                                isDownloadFinished = true;
                            } else if (status == DownloadManager.STATUS_FAILED) {
                                isDownloadFinished = true;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }



    private void openFile(Context context, Uri fileUri) {
        try {
            // Get MIME type
            ContentResolver contentResolver = context.getContentResolver();
            String mimeType = contentResolver.getType(fileUri);

            // Create intent to view the file
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, mimeType != null ? mimeType : "*/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Check if there's an app to handle the intent
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to open file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public static class ResourceViewHolder extends RecyclerView.ViewHolder {
        TextView resourceName, resourceUrl;
        ImageView resourceIcon;

        public ResourceViewHolder(View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.resource_name);
            resourceUrl = itemView.findViewById(R.id.resource_url);
            resourceIcon = itemView.findViewById(R.id.resource_icon);
        }
    }
}

