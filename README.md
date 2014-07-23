GridViewHeader
==============

Android GridView with addHeader( View view ) function


Usage
==============
```
View header = View.inflate( this, R.layout.item, null);
list.addHeaderView(header);
list.setNumColumns(2);
TestAdapter adapter = new TestAdapter(this, strings);
list.setAdapter(adapter);
```

Screenshot
==============

![ScreenShot](https://github.com/munix/GridViewHeader/blob/master/Screenshot_2014-01-09-14-43-55.png?raw=true)
