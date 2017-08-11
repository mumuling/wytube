package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/11.
 * 类 描 述:
 */

public class VisitorListBean {

    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"d75da4dc338a4def94f1f95628cbf2ad","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"43CA11D30230418898604AF6EF94EF6D","passType":"0","starttime":"2017-07-20 14:46:52.0","carLicense":null,"hours":"24","code":"111539","passMobile":"13995999906","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"13995999906"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"55098694891a45e782b69c8e0996f098","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"3E84AE45EABB4B09B6CB5267397A2A82","passType":"0","starttime":"2017-07-19 16:46:18.0","carLicense":null,"hours":"24","code":"177839","passMobile":"13980905857","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18602882326"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"7bf86e9723464ecd8f0d367f8d71f4f4","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"D66980A99E6E4948BA1EB6373655F0B2","passType":"0","starttime":"2017-07-18 15:07:12.0","carLicense":null,"hours":"24","code":"132434","passMobile":"18523570974","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523570000"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"b73e9def7f904edd9daa1f8128cf3f68","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-18 12:07:00.0","carLicense":null,"hours":"24","code":"120198","passMobile":"18523053781","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"e7dd718f5dea4707bf174ea4187033c1","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","passType":"0","starttime":"2017-07-12 15:26:40.0","carLicense":null,"hours":"24","code":"199937","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"移动端"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"f7ee245f8a754e05bbcc3fc4c731b9bf","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-12 14:20:57.0","carLicense":"渝D11111","hours":"24","code":"156673","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"31d813ffc84943298ff5450214c92bcc","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"6BF6F1E7170C4D75B92B328B18C4E1A0","passType":"0","starttime":"2017-07-07 15:50:04.0","carLicense":null,"hours":"24","code":"103840","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18696633476"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"c34c441c9a5346418aea9eb2407e41dd","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-07 15:46:28.0","carLicense":null,"hours":"24","code":"188778","passMobile":"15723075162","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"49e63852be584e09a605832100ad5af2","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"6BF6F1E7170C4D75B92B328B18C4E1A0","passType":"0","starttime":"2017-07-07 15:41:49.0","carLicense":null,"hours":"24","code":"109336","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18696633476"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"e6f1de421c7146b19eb7f6474522f23f","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"6BF6F1E7170C4D75B92B328B18C4E1A0","passType":"0","starttime":"2017-07-07 15:41:08.0","carLicense":null,"hours":"24","code":"168786","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18696633476"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"98e5168b969c482c99640a0b0a3b7da3","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"6BF6F1E7170C4D75B92B328B18C4E1A0","passType":"0","starttime":"2017-07-07 15:37:23.0","carLicense":null,"hours":"24","code":"143480","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18696633476"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"982e8dbedb9b4cac869c63adfb3510c7","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"6BF6F1E7170C4D75B92B328B18C4E1A0","passType":"0","starttime":"2017-07-07 15:34:46.0","carLicense":null,"hours":"24","code":"185906","passMobile":"18696334788","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18696633476"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"74b4b6ca872d4d5e96934aa39ac9a7e5","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-07 15:32:58.0","carLicense":null,"hours":"24","code":"178020","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"2a3a64f9bcb040538202af7a2ed2a591","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-07 11:12:55.0","carLicense":null,"hours":"24","code":"175126","passMobile":"15922636566","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"b25dd1e6014d4a46a6758a027e2acf5a","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-07 11:00:41.0","carLicense":"粤D12345","hours":"24","code":"140960","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"40dd216ce40e4b2da2e74173cb1ebd97","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-07 10:20:43.0","carLicense":null,"hours":"24","code":"143341","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"0d1a70ca86804e3a8e8bdbfb884bf5f3","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-07 10:20:09.0","carLicense":null,"hours":"24","code":"109224","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"db754fdf6e124157ba27eb7c3488cf2e","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-06 21:49:36.0","carLicense":null,"hours":"24","code":"176492","passMobile":"18723405521","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"145d043c67f142a38a0defd2d8306a73","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-05 23:16:59.0","carLicense":null,"hours":"24","code":"119194","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"467e86010d1f42ada021e69e42633d89","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-07-03 18:05:39.0","carLicense":null,"hours":"24","code":"139417","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"01cd4fc7d06d4641b6dba661da3e1cce","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-06-30 17:25:02.0","carLicense":null,"hours":"24","code":"102145","passMobile":"15782015451","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"fc5bbb2c2bf54776935f9c7f74f2a2b7","accessId":null,"numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","passType":"0","starttime":"2017-06-30 17:24:52.0","carLicense":null,"hours":"24","code":"191679","passMobile":"15782015452","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"07bd46b546594e0c8ae2345eda2ce879","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","passType":"0","starttime":"2017-06-29 17:30:22.0","carLicense":null,"hours":"24","code":"122334","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"移动端"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"6ed228cc52064252b1bdf19531f9722b","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-29 14:49:34.0","carLicense":null,"hours":"24","code":"173929","passMobile":"15723038206","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"f2714f248381487d8045d0bc68f09256","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"4A9989757AE5451DBE2382FBE19DC115","passType":"0","starttime":"2017-06-29 14:38:17.0","carLicense":null,"hours":"24","code":"197408","passMobile":"18716355688","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"12332112332"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"367ab086cd3d441e968a87e688aa2b8d","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"4A9989757AE5451DBE2382FBE19DC115","passType":"0","starttime":"2017-06-29 14:37:33.0","carLicense":null,"hours":"24","code":"153369","passMobile":"13435454654","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"12332112332"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"0615e0ed371b45848c03031f37498d73","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:48:42.0","carLicense":null,"hours":"24","code":"122471","passMobile":"18290232102","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"19e36ecb2bb346b1a59bec74a5e43a6f","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:47:26.0","carLicense":null,"hours":"24","code":"146135","passMobile":"17623656386","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"746effde6744458f828e899e15d3911a","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:42:46.0","carLicense":null,"hours":"24","code":"166617","passMobile":"17623656386","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"7fca4e15076b42cba592ff7e5d2bcb94","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:40:48.0","carLicense":null,"hours":"24","code":"147945","passMobile":"17623656386","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"c088289c1a6845eb927699d1de5dd9ae","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:37:04.0","carLicense":null,"hours":"24","code":"123826","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"2569bfe1c00f4315a0739376ae83532b","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:36:07.0","carLicense":null,"hours":"24","code":"167038","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"ed3bb7dc74a54b2198ae300957330924","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:11:04.0","carLicense":null,"hours":"24","code":"163779","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"847ef9529bc34d6b8f4339d56f9da742","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 15:03:46.0","carLicense":null,"hours":"24","code":"125744","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"3497c1ffbb174190a8f2109e863c57fd","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 14:48:10.0","carLicense":null,"hours":"24","code":"189726","passMobile":"13452512455","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"357a36ef33824043848f9145b6310adb","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"520A606868D44210A7E545436994FA3B","passType":"0","starttime":"2017-06-28 14:44:47.0","carLicense":null,"hours":"24","code":"186652","passMobile":"15723038506","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"18696633471"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"5fc45ba6ed91457fa65659cc5000d27c","accessId":null,"numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","passType":"0","starttime":"2017-06-26 15:03:42.0","carLicense":null,"hours":"24","code":"122208","passMobile":"18696633478","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"移动端"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"d3da8d55aa60454aa414f18e05880380","accessId":null,"numberId":"0043E20A28BA4446A8C70CE5836C5F6E","regUserId":"336DE6A59526484997083A172A1BE5E3","passType":"0","starttime":"2017-05-27 18:04:24.0","carLicense":null,"hours":"24","code":"164098","passMobile":"18883767153","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"3110","regUserName":"张欢"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"2b321375d61743568aee7db64e498ea8","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"474643D443704A118C67889730632830","passType":"0","starttime":"2017-05-27 10:36:27.0","carLicense":null,"hours":"24","code":"187204","passMobile":"17623253003","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"15923442776"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"ed21132f2db74de6b856a2830b97828b","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"474643D443704A118C67889730632830","passType":"0","starttime":"2017-05-27 10:36:09.0","carLicense":null,"hours":"24","code":"169230","passMobile":"17623253083","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"15923442776"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"521c9fae8e764990ad637d487c462adc","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"BD260A33D0D94D5FA3D70EAAF6AD1D46","passType":"0","starttime":"2017-05-27 09:47:30.0","carLicense":null,"hours":"24","code":"149890","passMobile":"18523053781","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523056781"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"73a13f8c145d4e3f957bf31dd7338a0d","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"BD260A33D0D94D5FA3D70EAAF6AD1D46","passType":"0","starttime":"2017-05-27 09:47:21.0","carLicense":null,"hours":"24","code":"141216","passMobile":"18523053784","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523056781"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"dfa46366873e454caca6c0abe406ec49","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"474643D443704A118C67889730632830","passType":"0","starttime":"2017-05-24 11:05:37.0","carLicense":null,"hours":"24","code":"137030","passMobile":"17623253003","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"15923442776"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"3658a811b6e5416e8c5b24dbaf497506","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"B6CD8F7DC656482A80CE886A80016748","passType":"0","starttime":"2017-05-17 11:08:25.0","carLicense":null,"hours":"24","code":"104765","passMobile":"18725854139","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523570927"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"passId":"e9ffea5527634f9abd0e57137032e299","accessId":null,"numberId":"C74C62B308014F61A69A4BA51B5CB7E3","regUserId":"B6CD8F7DC656482A80CE886A80016748","passType":"0","starttime":"2017-05-16 11:53:21.0","carLicense":null,"hours":"24","code":"100043","passMobile":"18725854139","cellId":null,"cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523570927"}]
     * date : 2017-08-11 10:57:51
     */

    private boolean success;
    private String message;
    private int code;
    private String date;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createDate : null
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark : null
         * passId : d75da4dc338a4def94f1f95628cbf2ad
         * accessId : null
         * numberId : C74C62B308014F61A69A4BA51B5CB7E3
         * regUserId : 43CA11D30230418898604AF6EF94EF6D
         * passType : 0
         * starttime : 2017-07-20 14:46:52.0
         * carLicense : null
         * hours : 24
         * code : 111539
         * passMobile : 13995999906
         * cellId : null
         * cellName : 重庆国际小区
         * buildingName : 1栋
         * unitName : 3单元
         * numberName : 3215
         * regUserName : 13995999906
         */

        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private Object remark;
        private String passId;
        private Object accessId;
        private String numberId;
        private String regUserId;
        private String passType;
        private String starttime;
        private Object carLicense;
        private String hours;
        private String code;
        private String passMobile;
        private Object cellId;
        private String cellName;
        private String buildingName;
        private String unitName;
        private String numberName;
        private String regUserName;

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Object modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getSorted() {
            return sorted;
        }

        public void setSorted(Object sorted) {
            this.sorted = sorted;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getPassId() {
            return passId;
        }

        public void setPassId(String passId) {
            this.passId = passId;
        }

        public Object getAccessId() {
            return accessId;
        }

        public void setAccessId(Object accessId) {
            this.accessId = accessId;
        }

        public String getNumberId() {
            return numberId;
        }

        public void setNumberId(String numberId) {
            this.numberId = numberId;
        }

        public String getRegUserId() {
            return regUserId;
        }

        public void setRegUserId(String regUserId) {
            this.regUserId = regUserId;
        }

        public String getPassType() {
            return passType;
        }

        public void setPassType(String passType) {
            this.passType = passType;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public Object getCarLicense() {
            return carLicense;
        }

        public void setCarLicense(Object carLicense) {
            this.carLicense = carLicense;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPassMobile() {
            return passMobile;
        }

        public void setPassMobile(String passMobile) {
            this.passMobile = passMobile;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getNumberName() {
            return numberName;
        }

        public void setNumberName(String numberName) {
            this.numberName = numberName;
        }

        public String getRegUserName() {
            return regUserName;
        }

        public void setRegUserName(String regUserName) {
            this.regUserName = regUserName;
        }
    }
}
