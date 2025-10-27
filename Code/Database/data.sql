SET FOREIGN_KEY_CHECKS=0;
USE blood_donation;
SHOW TABLES;

-- DOCTORS (10)
INSERT INTO Doctor (doctor_id, ssn, full_name, email, phone, specialization) VALUES
('DOC001','012345678901','Nguyễn Minh Tuấn','nguyenvtuan.doc1@example.com','0391123001','Hematology'),
('DOC002','012345678902','Trần Thị Hồng','tranthihong.doc2@example.com','0391123002','Transfusion Medicine'),
('DOC003','012345678903','Lê Văn Hùng','levanhung.doc3@example.com','0391123003','Internal Medicine'),
('DOC004','012345678904','Phạm Thị Lan','phamthilan.doc4@example.com','0391123004','Pathology'),
('DOC005','012345678905','Hoàng Văn Sơn','hoangvanson.doc5@example.com','0391123005','Emergency Medicine'),
('DOC006','012345678906','Vũ Thị Mai','vuthimai.doc6@example.com','0391123006','Pediatrics'),
('DOC007','012345678907','Đặng Quốc Anh','dangquocthanh.doc7@example.com','0391123007','Surgery'),
('DOC008','012345678908','Bùi Thị Nhung','buithinhung.doc8@example.com','0391123008','Anesthesiology'),
('DOC009','012345678909','Đỗ Văn Nam','dovannam.doc9@example.com','0391123009','Cardiology'),
('DOC010','012345678910','Ngô Thị Thanh','ngothithanh.doc10@example.com','0391123010','Obstetrics & Gynecology');

-- PATIENTS (50)
INSERT INTO Patients (patient_id, ssn, full_name, DateOfBirth, age, phone, email, Gender, status, donation_date) VALUES
('PAT001','10100010001','Nguyễn Văn An','1985-03-12',40,'0371234001','nguyenvanan.pat1@example.com','Male','New',NULL),
('PAT002','10100010002','Trần Thị Bích','1992-07-25',32,'0371234002','tranthibich.pat2@example.com','Female','New',NULL),
('PAT003','10100010003','Lê Minh Châu','2001-11-04',23,'0371234003','leminhchau.pat3@example.com','Male','New',NULL),
('PAT004','10100010004','Phạm Thị Dung','1978-02-18',47,'0371234004','phamthidung.pat4@example.com','Female','New',NULL),
('PAT005','10100010005','Hoàng Văn Đức','1969-09-30',55,'0371234005','hoangvanduc.pat5@example.com','Male','New',NULL),
('PAT006','10100010006','Vũ Thị Hạnh','1998-05-14',27,'0371234006','vuthihanh.pat6@example.com','Female','New',NULL),
('PAT007','10100010007','Đặng Văn Khoa','1989-01-20',36,'0371234007','dangvankhoa.pat7@example.com','Male','New',NULL),
('PAT008','10100010008','Bùi Thị Lệ','2005-10-03',19,'0371234008','buitile.pat8@example.com','Female','New',NULL),
('PAT009','10100010009','Đỗ Minh Long','1995-12-11',29,'0371234009','diminhlong.pat9@example.com','Male','New',NULL),
('PAT010','10100010010','Ngô Thị My','1982-06-01',42,'0371234010','ngothimy.pat10@example.com','Female','New',NULL),
('PAT011','10100010011','Phan Văn Nam','1975-08-22',50,'0371234011','phanvannam.pat11@example.com','Male','New',NULL),
('PAT012','10100010012','Trịnh Thị Oanh','1990-04-09',34,'0371234012','trinhthioanh.pat12@example.com','Female','New',NULL),
('PAT013','10100010013','Trương Minh Phúc','1987-11-29',37,'0371234013','truongminhphuc.pat13@example.com','Male','New',NULL),
('PAT014','10100010014','Lý Thị Quỳnh','1996-02-13',28,'0371234014','lythiquynh.pat14@example.com','Female','New',NULL),
('PAT015','10100010015','Võ Văn Sang','2000-09-08',24,'0371234015','vovansang.pat15@example.com','Male','New',NULL),
('PAT016','10100010016','Phạm Thị Tâm','1983-12-17',41,'0371234016','phamthitam.pat16@example.com','Female','New',NULL),
('PAT017','10100010017','Nguyễn Hữu Tiến','1994-03-05',31,'0371234017','nguyenhuutien.pat17@example.com','Male','New',NULL),
('PAT018','10100010018','Trần Văn Trung','1970-07-19',55,'0371234018','tranvantrung.pat18@example.com','Male','New',NULL),
('PAT019','10100010019','Lê Thị Uyên','1988-10-21',36,'0371234019','lethiyen.pat19@example.com','Female','New',NULL),
('PAT020','10100010020','Phạm Quang Vinh','1999-01-30',26,'0371234020','phamquangvinh.pat20@example.com','Male','New',NULL),
('PAT021','10100010021','Hoàng Thị Xuyến','1986-05-27',39,'0371234021','hoangthixuyen.pat21@example.com','Female','New',NULL),
('PAT022','10100010022','Vũ Minh Yên','2002-08-16',22,'0371234022','vuminhyen.pat22@example.com','Male','New',NULL),
('PAT023','10100010023','Đặng Thị Ánh','1991-09-02',33,'0371234023','dangthianh.pat23@example.com','Female','New',NULL),
('PAT024','10100010024','Bùi Văn Bình','1980-11-12',44,'0371234024','buivanbinh.pat24@example.com','Male','New',NULL),
('PAT025','10100010025','Đỗ Thị Cẩm','1973-04-04',52,'0371234025','dothicam.pat25@example.com','Female','New',NULL),
('PAT026','10100010026','Ngô Văn Duy','1997-06-06',28,'0371234026','ngovanduy.pat26@example.com','Male','New',NULL),
('PAT027','10100010027','Phan Thị Hương','1984-12-02',40,'0371234027','phanthihuong.pat27@example.com','Female','New',NULL),
('PAT028','10100010028','Trịnh Văn Kết','1993-03-15',31,'0371234028','trinhvanket.pat28@example.com','Male','New',NULL),
('PAT029','10100010029','Trương Thị Lan Anh','2004-07-07',21,'0371234029','truongthilananh.pat29@example.com','Female','New',NULL),
('PAT030','10100010030','Lê Văn Minh','1965-01-18',60,'0371234030','levanminh.pat30@example.com','Male','New',NULL),
('PAT031','10100010031','Nguyễn Thị Nga','1979-10-10',46,'0371234031','nguyenthinga.pat31@example.com','Female','New',NULL),
('PAT032','10100010032','Trần Văn Phong','1981-09-09',44,'0371234032','tranvanphong.pat32@example.com','Male','New',NULL),
('PAT033','10100010033','Lý Minh Quân','1995-02-02',30,'0371234033','lyminhquan.pat33@example.com','Male','New',NULL),
('PAT034','10100010034','Phạm Thị Quế','1990-06-24',35,'0371234034','phamthique.pat34@example.com','Female','New',NULL),
('PAT035','10100010035','Hoàng Văn Sang','1988-08-08',37,'0371234035','hoangvansang2.pat35@example.com','Male','New',NULL),
('PAT036','10100010036','Vũ Thị Thảo','1996-11-11',28,'0371234036','vuthithao.pat36@example.com','Female','New',NULL),
('PAT037','10100010037','Đặng Minh Trí','2003-05-05',22,'0371234037','dangminhtri.pat37@example.com','Male','New',NULL),
('PAT038','10100010038','Bùi Thị Uyên','1987-03-03',38,'0371234038','buithiyen.pat38@example.com','Female','New',NULL),
('PAT039','10100010039','Đỗ Văn Vượng','1992-12-12',32,'0371234039','dovamvuong.pat39@example.com','Male','New',NULL),
('PAT040','10100010040','Ngô Thị Yến','1985-04-04',40,'0371234040','ngothiyen.pat40@example.com','Female','New',NULL),
('PAT041','10100010041','Phan Văn Zách','1998-09-19',26,'0371234041','phanvanzach.pat41@example.com','Male','New',NULL),
('PAT042','10100010042','Trịnh Thị Ái','1976-02-26',49,'0371234042','trinhthiai.pat42@example.com','Female','New',NULL),
('PAT043','10100010043','Trương Minh Bình','1989-07-30',36,'0371234043','truongminhbinh.pat43@example.com','Male','New',NULL),
('PAT044','10100010044','Lê Thị Cúc','1994-10-14',30,'0371234044','lethicuc.pat44@example.com','Female','New',NULL),
('PAT045','10100010045','Nguyễn Văn Dũng','1968-01-05',57,'0371234045','nguyenvandung.pat45@example.com','Male','New',NULL),
('PAT046','10100010046','Trần Thị Én','2000-03-22',25,'0371234046','tranthien.pat46@example.com','Female','New',NULL),
('PAT047','10100010047','Phạm Minh Giang','1991-08-28',33,'0371234047','phamminhgiang.pat47@example.com','Male','New',NULL),
('PAT048','10100010048','Hoàng Thị Hòa','1983-06-16',41,'0371234048','hoangthihop.pat48@example.com','Female','New',NULL),
('PAT049','10100010049','Vũ Văn Khang','1997-11-02',27,'0371234049','vuvankhang.pat49@example.com','Male','New',NULL),
('PAT050','10100010050','Đặng Thị Lý','1984-05-30',40,'0371234050','dangthily.pat50@example.com','Female','New',NULL);

-- DONORS (50) - donors must be age between 18 and 100
INSERT INTO Donors (donors_id, ssn, full_name, DateOfBirth, age, phone, email, Gender, last_donation_date) VALUES
('DON001','20100020001','Nguyễn Thị Ánh','1990-02-20',35,'0382003001','nguyenthianh.don1@example.com','Female','2024-11-15'),
('DON002','20100020002','Trần Văn Bình','1987-08-08',38,'0382003002','tranvanbinh.don2@example.com','Male','2025-02-10'),
('DON003','20100020003','Lê Thị Cẩm','1995-03-03',30,'0382003003','lethicam.don3@example.com','Female','2024-06-01'),
('DON004','20100020004','Phạm Văn Dũng','1985-12-12',39,'0382003004','phamvandung.don4@example.com','Male','2025-01-20'),
('DON005','20100020005','Hoàng Thị Hạnh','1992-07-07',33,'0382003005','hoangthihanh.don5@example.com','Female','2024-09-05'),
('DON006','20100020006','Vũ Minh Hoàng','1980-05-05',45,'0382003006','vuminhhoang.don6@example.com','Male','2024-03-18'),
('DON007','20100020007','Đặng Thị Hương','1998-11-11',26,'0382003007','dangthihuong.don7@example.com','Female','2024-12-30'),
('DON008','20100020008','Bùi Văn Khoa','1993-04-04',31,'0382003008','buivankhoa.don8@example.com','Male','2025-03-02'),
('DON009','20100020009','Đỗ Thị Lan','1989-10-10',36,'0382003009','dothilan.don9@example.com','Female','2024-07-07'),
('DON010','20100020010','Ngô Minh Long','1996-01-01',29,'0382003010','ngominhlong.don10@example.com','Male','2024-05-25'),
('DON011','20100020011','Phan Thị My','1994-09-17',30,'0382003011','phanthimy.don11@example.com','Female','2024-08-08'),
('DON012','20100020012','Trịnh Văn Nam','1977-06-06',48,'0382003012','trinhvannam.don12@example.com','Male','2024-02-14'),
('DON013','20100020013','Trương Thị Oanh','1999-12-12',25,'0382003013','truongthioanh.don13@example.com','Female','2025-04-01'),
('DON014','20100020014','Lý Minh Phúc','1988-03-03',37,'0382003014','lyminhphuc.don14@example.com','Male','2024-10-10'),
('DON015','20100020015','Võ Thị Quỳnh','1991-07-07',33,'0382003015','vothiquynh.don15@example.com','Female','2025-06-12'),
('DON016','20100020016','Phạm Minh Sang','1986-11-11',38,'0382003016','phamminhsang.don16@example.com','Male','2024-04-04'),
('DON017','20100020017','Nguyễn Thị Thảo','1993-05-05',31,'0382003017','nguyenthithao.don17@example.com','Female','2024-01-20'),
('DON018','20100020018','Trần Văn Tiến','1984-02-02',41,'0382003018','tranvantien.don18@example.com','Male','2024-09-09'),
('DON019','20100020019','Lê Thị Uyên','1997-08-08',27,'0382003019','lethiyen.don19@example.com','Female','2024-11-01'),
('DON020','20100020020','Phạm Văn Vinh','1982-10-10',43,'0382003020','phamvanvinh.don20@example.com','Male','2024-06-15'),
('DON021','20100020021','Hoàng Minh Xuân','1990-04-04',35,'0382003021','hoangminhxuan.don21@example.com','Male','2025-02-28'),
('DON022','20100020022','Vũ Thị Yến','1995-09-09',29,'0382003022','vuthiyen.don22@example.com','Female','2024-07-20'),
('DON023','20100020023','Đặng Văn Ánh','1989-01-01',36,'0382003023','dangvananh.don23@example.com','Male','2024-12-01'),
('DON024','20100020024','Bùi Minh Bình','1992-02-02',33,'0382003024','buiminhbinh.don24@example.com','Male','2024-05-05'),
('DON025','20100020025','Đỗ Thị Chi','1998-03-03',27,'0382003025','dothichi.don25@example.com','Female','2025-01-01'),
('DON026','20100020026','Ngô Văn Dũng','1983-06-06',42,'0382003026','ngovandung.don26@example.com','Male','2024-08-18'),
('DON027','20100020027','Phan Thị Hồng','1996-12-12',28,'0382003027','phanthihong.don27@example.com','Female','2024-09-30'),
('DON028','20100020028','Trịnh Minh Khang','1991-11-11',33,'0382003028','trinhminhkhang.don28@example.com','Male','2024-03-03'),
('DON029','20100020029','Trương Thị Linh','1994-01-20',31,'0382003029','truongthilinh.don29@example.com','Female','2024-02-20'),
('DON030','20100020030','Lý Văn Mạnh','1985-07-07',39,'0382003030','lyvanmanh.don30@example.com','Male','2024-06-06'),
('DON031','20100020031','Võ Thị Ngọc','1999-09-09',25,'0382003031','vothingoc.don31@example.com','Female','2024-11-11'),
('DON032','20100020032','Phạm Văn Phúc','1990-10-10',35,'0382003032','phamvanphuc.don32@example.com','Male','2025-03-05'),
('DON033','20100020033','Nguyễn Hồng Quân','1988-05-05',37,'0382003033','nguyenhongquan.don33@example.com','Male','2024-10-01'),
('DON034','20100020034','Trần Thị Quế','1993-02-02',31,'0382003034','tranthique.don34@example.com','Female','2024-12-12'),
('DON035','20100020035','Lê Văn Sang','1986-08-08',38,'0382003035','levansang.don35@example.com','Male','2024-09-09'),
('DON036','20100020036','Phạm Thị Thủy','1997-04-04',27,'0382003036','phamthithuy.don36@example.com','Female','2024-07-07'),
('DON037','20100020037','Hoàng Văn Toàn','1992-03-03',33,'0382003037','hoangvantoan.don37@example.com','Male','2024-05-15'),
('DON038','20100020038','Vũ Thị Uyên','1991-06-06',34,'0382003038','vuthiyen.don38@example.com','Female','2024-08-08'),
('DON039','20100020039','Đặng Minh Vũ','1984-09-09',41,'0382003039','dangminhvu.don39@example.com','Male','2024-04-04'),
('DON040','20100020040','Bùi Thị Yến','1995-12-12',29,'0382003040','buithiyen.don40@example.com','Female','2024-03-03'),
('DON041','20100020041','Đỗ Văn Zĩ','1987-01-01',38,'0382003041','dovanzi.don41@example.com','Male','2025-05-05'),
('DON042','20100020042','Ngô Thị Ánh','1990-02-02',35,'0382003042','ngothianh.don42@example.com','Female','2024-11-20'),
('DON043','20100020043','Phan Minh Bình','1998-10-10',25,'0382003043','phanminhbinh.don43@example.com','Male','2024-06-30'),
('DON044','20100020044','Trịnh Thị Cẩm','1989-08-08',36,'0382003044','trinhthicam.don44@example.com','Female','2024-09-01'),
('DON045','20100020045','Trương Văn Dương','1994-05-05',30,'0382003045','truongvanduong.don45@example.com','Male','2024-02-02'),
('DON046','20100020046','Lý Thị E','1996-06-06',28,'0382003046','lythie.don46@example.com','Female','2024-12-24'),
('DON047','20100020047','Võ Minh Giao','1983-11-11',41,'0382003047','vominhgiao.don47@example.com','Male','2024-10-20'),
('DON048','20100020048','Phạm Thị Hạ','1997-09-09',27,'0382003048','phamthihạ.don48@example.com','Female','2024-07-14'),
('DON049','20100020049','Hoàng Văn Khải','1982-03-03',43,'0382003049','hoangvankhai.don49@example.com','Male','2024-05-05'),
('DON050','20100020050','Vũ Thị Lan','1999-01-15',26,'0382003050','vuthilan.don50@example.com','Female','2025-01-15');

-- ACCOUNTS for Patients and Doctors
-- Patients accounts (patient_id column populated)
INSERT INTO ACCOUNTT (id, email, password, role, is_active, patient_id, doctor_id) VALUES
('ACCPAT001','nguyenvanan.pat1@example.com','$2y$10$examplehash1','Patient',TRUE,'PAT001',NULL),
('ACCPAT002','tranthibich.pat2@example.com','$2y$10$examplehash2','Patient',TRUE,'PAT002',NULL),
('ACCPAT003','leminhchau.pat3@example.com','$2y$10$examplehash3','Patient',TRUE,'PAT003',NULL),
('ACCPAT004','phamthidung.pat4@example.com','$2y$10$examplehash4','Patient',TRUE,'PAT004',NULL),
('ACCPAT005','hoangvanduc.pat5@example.com','$2y$10$examplehash5','Patient',TRUE,'PAT005',NULL),
('ACCPAT006','vuthihanh.pat6@example.com','$2y$10$examplehash6','Patient',TRUE,'PAT006',NULL),
('ACCPAT007','dangvankhoa.pat7@example.com','$2y$10$examplehash7','Patient',TRUE,'PAT007',NULL),
('ACCPAT008','buitile.pat8@example.com','$2y$10$examplehash8','Patient',TRUE,'PAT008',NULL),
('ACCPAT009','diminhlong.pat9@example.com','$2y$10$examplehash9','Patient',TRUE,'PAT009',NULL),
('ACCPAT010','ngothimy.pat10@example.com','$2y$10$examplehash10','Patient',TRUE,'PAT010',NULL),
('ACCPAT011','phanvannam.pat11@example.com','$2y$10$examplehash11','Patient',TRUE,'PAT011',NULL),
('ACCPAT012','trinhthioanh.pat12@example.com','$2y$10$examplehash12','Patient',TRUE,'PAT012',NULL),
('ACCPAT013','truongminhphuc.pat13@example.com','$2y$10$examplehash13','Patient',TRUE,'PAT013',NULL),
('ACCPAT014','lythiquynh.pat14@example.com','$2y$10$examplehash14','Patient',TRUE,'PAT014',NULL),
('ACCPAT015','vovansang.pat15@example.com','$2y$10$examplehash15','Patient',TRUE,'PAT015',NULL),
('ACCPAT016','phamthitam.pat16@example.com','$2y$10$examplehash16','Patient',TRUE,'PAT016',NULL),
('ACCPAT017','nguyenhuutien.pat17@example.com','$2y$10$examplehash17','Patient',TRUE,'PAT017',NULL),
('ACCPAT018','tranvantrung.pat18@example.com','$2y$10$examplehash18','Patient',TRUE,'PAT018',NULL),
('ACCPAT019','lethiyen.pat19@example.com','$2y$10$examplehash19','Patient',TRUE,'PAT019',NULL),
('ACCPAT020','phamquangvinh.pat20@example.com','$2y$10$examplehash20','Patient',TRUE,'PAT020',NULL),
('ACCPAT021','hoangthixuyen.pat21@example.com','$2y$10$examplehash21','Patient',TRUE,'PAT021',NULL),
('ACCPAT022','vuminhyen.pat22@example.com','$2y$10$examplehash22','Patient',TRUE,'PAT022',NULL),
('ACCPAT023','dangthianh.pat23@example.com','$2y$10$examplehash23','Patient',TRUE,'PAT023',NULL),
('ACCPAT024','buivanbinh.pat24@example.com','$2y$10$examplehash24','Patient',TRUE,'PAT024',NULL),
('ACCPAT025','dothicam.pat25@example.com','$2y$10$examplehash25','Patient',TRUE,'PAT025',NULL),
('ACCPAT026','ngovanduy.pat26@example.com','$2y$10$examplehash26','Patient',TRUE,'PAT026',NULL),
('ACCPAT027','phanthihuong.pat27@example.com','$2y$10$examplehash27','Patient',TRUE,'PAT027',NULL),
('ACCPAT028','trinhvanket.pat28@example.com','$2y$10$examplehash28','Patient',TRUE,'PAT028',NULL),
('ACCPAT029','truongthilananh.pat29@example.com','$2y$10$examplehash29','Patient',TRUE,'PAT029',NULL),
('ACCPAT030','levanminh.pat30@example.com','$2y$10$examplehash30','Patient',TRUE,'PAT030',NULL),
('ACCPAT031','nguyenthinga.pat31@example.com','$2y$10$examplehash31','Patient',TRUE,'PAT031',NULL),
('ACCPAT032','tranvanphong.pat32@example.com','$2y$10$examplehash32','Patient',TRUE,'PAT032',NULL),
('ACCPAT033','lyminhquan.pat33@example.com','$2y$10$examplehash33','Patient',TRUE,'PAT033',NULL),
('ACCPAT034','phamthique.pat34@example.com','$2y$10$examplehash34','Patient',TRUE,'PAT034',NULL),
('ACCPAT035','hoangvansang2.pat35@example.com','$2y$10$examplehash35','Patient',TRUE,'PAT035',NULL),
('ACCPAT036','vuthithao.pat36@example.com','$2y$10$examplehash36','Patient',TRUE,'PAT036',NULL),
('ACCPAT037','dangminhtri.pat37@example.com','$2y$10$examplehash37','Patient',TRUE,'PAT037',NULL),
('ACCPAT038','buithiyen.pat38@example.com','$2y$10$examplehash38','Patient',TRUE,'PAT038',NULL),
('ACCPAT039','dovamvuong.pat39@example.com','$2y$10$examplehash39','Patient',TRUE,'PAT039',NULL),
('ACCPAT040','ngothiyen.pat40@example.com','$2y$10$examplehash40','Patient',TRUE,'PAT040',NULL),
('ACCPAT041','phanvanzach.pat41@example.com','$2y$10$examplehash41','Patient',TRUE,'PAT041',NULL),
('ACCPAT042','trinhthiai.pat42@example.com','$2y$10$examplehash42','Patient',TRUE,'PAT042',NULL),
('ACCPAT043','truongminhbinh.pat43@example.com','$2y$10$examplehash43','Patient',TRUE,'PAT043',NULL),
('ACCPAT044','lethicuc.pat44@example.com','$2y$10$examplehash44','Patient',TRUE,'PAT044',NULL),
('ACCPAT045','nguyenvandung.pat45@example.com','$2y$10$examplehash45','Patient',TRUE,'PAT045',NULL),
('ACCPAT046','tranthien.pat46@example.com','$2y$10$examplehash46','Patient',TRUE,'PAT046',NULL),
('ACCPAT047','phamminhgiang.pat47@example.com','$2y$10$examplehash47','Patient',TRUE,'PAT047',NULL),
('ACCPAT048','hoangthihop.pat48@example.com','$2y$10$examplehash48','Patient',TRUE,'PAT048',NULL),
('ACCPAT049','vuvankhang.pat49@example.com','$2y$10$examplehash49','Patient',TRUE,'PAT049',NULL),
('ACCPAT050','dangthily.pat50@example.com','$2y$10$examplehash50','Patient',TRUE,'PAT050',NULL);

-- Doctors accounts (doctor_id column populated)
INSERT INTO ACCOUNTT (id, email, password, role, is_active, patient_id, doctor_id) VALUES
('ACCDOC001','nguyenvtuan.doc1@example.com','$2y$10$docpass1','Doctor',TRUE,NULL,'DOC001'),
('ACCDOC002','tranthihong.doc2@example.com','$2y$10$docpass2','Doctor',TRUE,NULL,'DOC002'),
('ACCDOC003','levanhung.doc3@example.com','$2y$10$docpass3','Doctor',TRUE,NULL,'DOC003'),
('ACCDOC004','phamthilan.doc4@example.com','$2y$10$docpass4','Doctor',TRUE,NULL,'DOC004'),
('ACCDOC005','hoangvanson.doc5@example.com','$2y$10$docpass5','Doctor',TRUE,NULL,'DOC005'),
('ACCDOC006','vuthimai.doc6@example.com','$2y$10$docpass6','Doctor',TRUE,NULL,'DOC006'),
('ACCDOC007','dangquocthanh.doc7@example.com','$2y$10$docpass7','Doctor',TRUE,NULL,'DOC007'),
('ACCDOC008','buithinhung.doc8@example.com','$2y$10$docpass8','Doctor',TRUE,NULL,'DOC008'),
('ACCDOC009','dovannam.doc9@example.com','$2y$10$docpass9','Doctor',TRUE,NULL,'DOC009'),
('ACCDOC010','ngothithanh.doc10@example.com','$2y$10$docpass10','Doctor',TRUE,NULL,'DOC010');

-- BLOOD_BANK
INSERT INTO BLOOD_BANK (bank_id, bank_name, location, contact_phone, contact_email, volume, assigned_doctor)
VALUES
('BANK001', 'Central Blood Bank', 'Hanoi', '0901234567', 'central@bloodbank.vn', 120.5, 'DOC001'),
('BANK002', 'Saigon Blood Center', 'Ho Chi Minh City', '0902345678', 'saigon@bloodbank.vn', 95.2, 'DOC002'),
('BANK003', 'Danang Life Blood Bank', 'Danang', '0903456789', 'danang@bloodbank.vn', 80.4, 'DOC003'),
('BANK004', 'Hue Medical Blood Bank', 'Hue', '0904567890', 'hue@bloodbank.vn', 70.0, 'DOC004'),
('BANK005', 'Can Tho Blood Center', 'Can Tho', '0905678901', 'cantho@bloodbank.vn', 66.5, 'DOC005');

-- DOCTOR_BLOODBANK (liên kết doctor với bank)
INSERT INTO Doctor_BloodBank (doctor_id, bank_id)
VALUES
('DOC001', 'BANK001'),
('DOC002', 'BANK002'),
('DOC003', 'BANK003'),
('DOC004', 'BANK004'),
('DOC005', 'BANK005');

-- BLOOD INVENTORY
INSERT INTO Blood_Inventory (unit_id, blood_type, RH, volume_Litter, donated_date, expired_date, status, bank_id)
VALUES
('UNIT001', 'A+', '+', 0.45, '2025-09-01', '2026-03-01', 'available', 'BANK001'),
('UNIT002', 'O+', '+', 0.50, '2025-08-15', '2026-02-15', 'used', 'BANK001'),
('UNIT003', 'B-', '-', 0.47, '2025-07-10', '2026-01-10', 'available', 'BANK002'),
('UNIT004', 'AB+', '+', 0.48, '2025-09-20', '2026-03-20', 'available', 'BANK003'),
('UNIT005', 'A-', '-', 0.52, '2025-10-01', '2026-04-01', 'available', 'BANK004'),
('UNIT006', 'O-', '-', 0.43, '2025-08-25', '2026-02-25', 'expired', 'BANK005');

-- DONATION EVENT
INSERT INTO Donation_Event (donation_id, Date_event, location, volume_collected, bank_id)
VALUES
('DONATE001', '2025-09-15', 'Hanoi', 10.5, 'BANK001'),
('DONATE002', '2025-09-20', 'HCMC', 12.0, 'BANK002'),
('DONATE003', '2025-09-22', 'Danang', 9.0, 'BANK003'),
('DONATE004', '2025-09-25', 'Hue', 11.3, 'BANK004'),
('DONATE005', '2025-09-27', 'Can Tho', 8.6, 'BANK005');

-- DONORS_DONATIONEVENT
INSERT INTO Donors_DonationEvent (donors_id, donation_id)
VALUES
('DON001', 'DONATE001'),
('DON002', 'DONATE001'),
('DON003', 'DONATE002'),
('DON004', 'DONATE003'),
('DON005', 'DONATE004'),
('DON006', 'DONATE005');

-- REQUEST
INSERT INTO Request (request_id, bank_id, blood_type, quantity, status, date_request, date_response, fulfilled_inventory_id, patient_id, doctor_id, donors_id)
VALUES
('REQ001', 1, 'A+', 2, 'Approved', '2025-09-25', '2025-09-27 10:30:00', 1, 'PAT001', 'DOC001', 'DON001'),
('REQ002', 2, 'O+', 3, 'Pending', '2025-09-26', NULL, NULL, 'PAT002', 'DOC002', 'DON002'),
('REQ003', 3, 'B-', 1, 'Fulfilled', '2025-09-27', '2025-09-28 09:45:00', 3, 'PAT003', 'DOC003', 'DON003'),
('REQ004', 4, 'AB+', 4, 'Rejected', '2025-09-28', '2025-09-29 15:00:00', NULL, 'PAT004', 'DOC004', 'DON004'),
('REQ005', 5, 'O-', 2, 'Approved', '2025-09-30', '2025-10-01 11:00:00', 6, 'PAT005', 'DOC005', 'DON005');

SET FOREIGN_KEY_CHECKS=1;

