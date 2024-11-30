USTHConnect FrontEnd
### Complete:
- UI for: Home(except Notification), Schedule, Campus, Resource, StudyBuddy(except StudyBuddy and Message) Login
- RecyclerView & SearchView: Campus, Schedule, Resource

### In Progress:
- Improve UI: lenght (padding), width, ui, color (rename color: Primary, ....), UI have backbutton => Linear to Frame, chỉnh lại height của header

- Continue: StudyBuddy UI, cần dùng 1 component: bấm vào để sang trái phải,áp dụng cho lịch, bulding

- Update edit profile and profile StudyBuddy: fetch url image

- Notification: PushNotification: Tincoder

- Click to add course to Favorite

### Note:
+ Chưa đổi đc tên các môn học trong từng Program: do Layout cứng 

+ Set cho nhiều đối tượng: 
    TextView building_name = findViewById(R.id.detail_building_name);
    String name = getIntent().getStringExtra("Building Name");
    building_name.setText(name);