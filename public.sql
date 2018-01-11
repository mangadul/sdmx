/*
Navicat PGSQL Data Transfer

Source Server         : Localhost
Source Server Version : 90602
Source Host           : localhost:5432
Source Database       : web_portal
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90602
File Encoding         : 65001

Date: 2017-12-29 17:19:02
*/


-- ----------------------------
-- Sequence structure for account_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."account_id_seq";
CREATE SEQUENCE "public"."account_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 9
 CACHE 1;
SELECT setval('"public"."account_id_seq"', 9, true);

-- ----------------------------
-- Sequence structure for category_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."category_id_seq";
CREATE SEQUENCE "public"."category_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 18
 CACHE 1;
SELECT setval('"public"."category_id_seq"', 18, true);

-- ----------------------------
-- Sequence structure for data_flow_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."data_flow_id_seq";
CREATE SEQUENCE "public"."data_flow_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for data_flow_id_seq1
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."data_flow_id_seq1";
CREATE SEQUENCE "public"."data_flow_id_seq1"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for data_set_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."data_set_id_seq";
CREATE SEQUENCE "public"."data_set_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for data_set_id_seq1
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."data_set_id_seq1";
CREATE SEQUENCE "public"."data_set_id_seq1"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 10
 CACHE 1;
SELECT setval('"public"."data_set_id_seq1"', 10, true);

-- ----------------------------
-- Sequence structure for hibernate_sequence
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."hibernate_sequence";
CREATE SEQUENCE "public"."hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1005
 CACHE 1;
SELECT setval('"public"."hibernate_sequence"', 1005, true);

-- ----------------------------
-- Sequence structure for news_approval_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."news_approval_id_seq";
CREATE SEQUENCE "public"."news_approval_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1;
SELECT setval('"public"."news_approval_id_seq"', 2, true);

-- ----------------------------
-- Sequence structure for news_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."news_id_seq";
CREATE SEQUENCE "public"."news_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1;
SELECT setval('"public"."news_id_seq"', 7, true);

-- ----------------------------
-- Sequence structure for publication_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."publication_id_seq";
CREATE SEQUENCE "public"."publication_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 12
 CACHE 1;
SELECT setval('"public"."publication_id_seq"', 12, true);

-- ----------------------------
-- Sequence structure for publicationapproval_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."publicationapproval_id_seq";
CREATE SEQUENCE "public"."publicationapproval_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1;
SELECT setval('"public"."publicationapproval_id_seq"', 2, true);

-- ----------------------------
-- Sequence structure for role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."role_id_seq";
CREATE SEQUENCE "public"."role_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 9
 CACHE 1;
SELECT setval('"public"."role_id_seq"', 9, true);

-- ----------------------------
-- Sequence structure for variable_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."variable_id_seq";
CREATE SEQUENCE "public"."variable_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1;
SELECT setval('"public"."variable_id_seq"', 2, true);

-- ----------------------------
-- Sequence structure for variable_id_seq1
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."variable_id_seq1";
CREATE SEQUENCE "public"."variable_id_seq1"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS "public"."account";
CREATE TABLE "public"."account" (
"id" int8 DEFAULT nextval('account_id_seq'::regclass) NOT NULL,
"created" timestamp(6),
"email" varchar(255) COLLATE "default" NOT NULL,
"password" varchar(255) COLLATE "default" NOT NULL,
"name" varchar(255) COLLATE "default" DEFAULT ''::character varying
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO "public"."account" VALUES ('1', '2017-12-02 09:53:30.81', 'user', '$2a$10$gY8BdnWb9M3AAItAFRtU/u2md3toNw6BLN4YG9mTvW23lr/pAXOJa', 'User');
INSERT INTO "public"."account" VALUES ('2', '2017-12-02 09:53:31.175', 'admin', '$2a$10$afvbocJsEHzA9iTy5OqECul/4tqSpENIw0MJ5whh1IHe/eIgz1ULG', 'Administrator');
INSERT INTO "public"."account" VALUES ('5', '2017-12-14 18:14:04.216', 'admin@app.com', '$2a$10$A9kS8wHLgTWLuYaDLCRdZeThwv.J3gDhmlAQlaK2mmqt.LyPfxsk.', 'Admin Istrator');
INSERT INTO "public"."account" VALUES ('6', '2017-12-16 21:11:12.388', 'test@gmailxx.com', '$2a$10$3LumFcABB1EQnUqEU6mTvu.7H0KrW16vkRN0pMyxDKaPc8SqRUrDO', 'Test');
INSERT INTO "public"."account" VALUES ('7', '2017-12-22 10:52:45.978', 'ccc', '$2a$10$O3Buuq8VerslwIpME.BJ6uNv4Vd/oR/f6YODehHLpZW7TdPXCdRcm', 'ccc');
INSERT INTO "public"."account" VALUES ('8', '2017-12-25 11:19:57.149', 'lkjlkjx', '$2a$10$3B1H1rp5cRhSLP7QhyUTW.fyBx7HCClzGBhDeG6oK97EJWhoE1dfO', 'asdx');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS "public"."category";
CREATE TABLE "public"."category" (
"id" int8 DEFAULT nextval('category_id_seq'::regclass) NOT NULL,
"name" varchar(255) COLLATE "default" NOT NULL,
"parent_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO "public"."category" VALUES ('1', 'Publicationx', null);
INSERT INTO "public"."category" VALUES ('2', 'Exchange Rate Policy', null);
INSERT INTO "public"."category" VALUES ('3', 'Money, Credit and Banking', null);
INSERT INTO "public"."category" VALUES ('4', 'Financial Corporations', null);
INSERT INTO "public"."category" VALUES ('5', 'Financial Markets', null);
INSERT INTO "public"."category" VALUES ('6', 'Macroeconomic', null);
INSERT INTO "public"."category" VALUES ('7', 'Balance of Payments', null);
INSERT INTO "public"."category" VALUES ('8', 'Supervisory', null);
INSERT INTO "public"."category" VALUES ('9', 'Payment Statistics', null);
INSERT INTO "public"."category" VALUES ('10', 'BI Surveys', null);
INSERT INTO "public"."category" VALUES ('11', 'SEKI', '1');
INSERT INTO "public"."category" VALUES ('12', 'ITEMS', '1');
INSERT INTO "public"."category" VALUES ('13', 'SEKDA', '11');
INSERT INTO "public"."category" VALUES ('15', 'asdasd', null);
INSERT INTO "public"."category" VALUES ('17', 'asdasdasd', '2');
INSERT INTO "public"."category" VALUES ('18', 'ccasca', '10');

-- ----------------------------
-- Table structure for category_data_flow
-- ----------------------------
DROP TABLE IF EXISTS "public"."category_data_flow";
CREATE TABLE "public"."category_data_flow" (
"data_flow_id" int8 NOT NULL,
"category_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of category_data_flow
-- ----------------------------

-- ----------------------------
-- Table structure for category_news
-- ----------------------------
DROP TABLE IF EXISTS "public"."category_news";
CREATE TABLE "public"."category_news" (
"category_id" int8 NOT NULL,
"news_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of category_news
-- ----------------------------
INSERT INTO "public"."category_news" VALUES ('2', '1');
INSERT INTO "public"."category_news" VALUES ('4', '7');
INSERT INTO "public"."category_news" VALUES ('4', '10');
INSERT INTO "public"."category_news" VALUES ('4', '17');

-- ----------------------------
-- Table structure for category_publication
-- ----------------------------
DROP TABLE IF EXISTS "public"."category_publication";
CREATE TABLE "public"."category_publication" (
"category_id" int8 NOT NULL,
"publication_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of category_publication
-- ----------------------------

-- ----------------------------
-- Table structure for data_flow
-- ----------------------------
DROP TABLE IF EXISTS "public"."data_flow";
CREATE TABLE "public"."data_flow" (
"id" int8 DEFAULT nextval('data_flow_id_seq1'::regclass) NOT NULL,
"note" text COLLATE "default",
"meta" jsonb,
"title" varchar(255) COLLATE "default" NOT NULL,
"created" timestamp(6),
"updated" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of data_flow
-- ----------------------------
INSERT INTO "public"."data_flow" VALUES ('1', ';lkajsdf l;aksdjf;aoisdflaksjdfisjdflkjsdf osidjflksdjf oisjdf', '{"author": "admin"}', 'Money & Banking', '2017-12-14 15:39:25', null);
INSERT INTO "public"."data_flow" VALUES ('2', 'klqwelkqw elqkwje qowieqwlkejqw eoiqwjelqkwjeqlkwejoi', '{"author": "asd"}', 'Balance of Payments', '2017-12-27 15:39:30', null);

-- ----------------------------
-- Table structure for data_flow_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."data_flow_item";
CREATE TABLE "public"."data_flow_item" (
"data_flow_id" int8 NOT NULL,
"variable_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of data_flow_item
-- ----------------------------
INSERT INTO "public"."data_flow_item" VALUES ('1', '1');
INSERT INTO "public"."data_flow_item" VALUES ('1', '2');
INSERT INTO "public"."data_flow_item" VALUES ('2', '1');
INSERT INTO "public"."data_flow_item" VALUES ('2', '2');

-- ----------------------------
-- Table structure for data_set
-- ----------------------------
DROP TABLE IF EXISTS "public"."data_set";
CREATE TABLE "public"."data_set" (
"id" int8 DEFAULT nextval('data_set_id_seq1'::regclass) NOT NULL,
"attributes" jsonb,
"month" varchar(2) COLLATE "default" NOT NULL,
"value" numeric(19,2),
"year" varchar(4) COLLATE "default" NOT NULL,
"variable_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of data_set
-- ----------------------------
INSERT INTO "public"."data_set" VALUES ('1', '{"country": "INA"}', '01', '12345.00', '2017', '1');
INSERT INTO "public"."data_set" VALUES ('2', '{"country": "INA"}', '02', '78956.00', '2017', '1');
INSERT INTO "public"."data_set" VALUES ('3', '{"country": "INA"}', '03', '65856.00', '2017', '1');
INSERT INTO "public"."data_set" VALUES ('4', '{"country": "INA"}', '04', '12345.00', '2017', '1');
INSERT INTO "public"."data_set" VALUES ('5', '{"country": "INA"}', '05', '5345.00', '2017', '1');
INSERT INTO "public"."data_set" VALUES ('6', '{"country": "INA"}', '01', '77867.00', '2017', '2');
INSERT INTO "public"."data_set" VALUES ('7', '{"country": "INA"}', '02', '12334.00', '2017', '2');
INSERT INTO "public"."data_set" VALUES ('8', '{"country": "INA"}', '03', '34534.00', '2017', '2');
INSERT INTO "public"."data_set" VALUES ('9', '{"country": "INA"}', '04', '34534.00', '2017', '2');
INSERT INTO "public"."data_set" VALUES ('10', '{"country": "INA"}', '05', '45567.00', '2017', '2');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS "public"."news";
CREATE TABLE "public"."news" (
"id" int8 DEFAULT nextval('news_id_seq'::regclass) NOT NULL,
"content" text COLLATE "default",
"created" timestamp(6),
"title" varchar(255) COLLATE "default" NOT NULL,
"updated" timestamp(6),
"account_id" int8 NOT NULL,
"status" char(1) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO "public"."news" VALUES ('1', '<p><span>asdflkjsd<b><u><i>lfkjsd</i></u></b>f</span></p>', '2017-12-09 15:40:33.904', 'Bank Indonesia', '2017-12-09 15:40:33.904', '2', '1');
INSERT INTO "public"."news" VALUES ('2', '<h3>Abstraksi</h3><p>- Nilai Tukar Petani (NTP) adalah perbandingan indeks harga yang diterima petani (It) terhadap indeks harga yang dibayar petani (Ib).</p><p>- NTP merupakan salah satu indikator untuk melihat tingkat kemampuan/daya beli petani di perdesaan. NTP juga menunjukkan daya tukar (terms of trade) dari produk pertanian dengan barang dan jasa yang dikonsumsi maupun untuk biaya produksi. Semakin tinggi NTP, secara relatif semakin kuat pula tingkat kemampuan/daya beli petani.</p><p>- NTP nasional November 2017 sebesar 103,07 atau naik 0,28 persen dibanding NTP bulan sebelumnya. Kenaikan NTP dikarenakan Indeks Harga yang Diterima Petani (It) naik sebesar 0,57 persen lebih besar dari kenaikan Indeks Harga yang Dibayar Petani (Ib) sebesar 0,29 persen.</p><p>- Pada November 2017, NTP Provinsi Riau mengalami kenaikan tertinggi (1,95 persen) dibandingkan kenaikan NTP provinsi lainnya. Sebaliknya, NTP Provinsi Kepulauan Bangka Belitung mengalami penurunan terbesar (1,73 persen) dibandingkan penurunan NTP provinsi lainnya.</p><p>- Pada November 2017, terjadi inflasi perdesaan di Indonesia sebesar 0,36 persen disebabkan oleh naiknya indeks di seluruh kelompok penyusun Indeks Konsumsi Rumah Tangga (IKRT), terutama Kelompok Bahan Makanan.</p><p>- Nilai Tukar Usaha Rumah Tangga Pertanian (NTUP) nasional November 2017 sebesar 111,72 atau naik 0,42 persen dibanding NTUP bulan sebelumnya.</p><p><span></span></p>', '2017-12-12 22:58:24.532', 'Nilai Tukar Petani (NTP) November 2017 sebesar 103,07 atau naik 0,28 persen', '2017-12-29 11:16:18.549', '2', '0');
INSERT INTO "public"."news" VALUES ('3', '<h3><span></span>Abstraksi</h3><p>Rupiah terdepresiasi 1,27 persen terhadap dolar Amerika pada Oktober 2017&nbsp; dengan nilai tukar sebesar Rp13.523,07 per dolar Amerika. Menurut provinsi, level terendah&nbsp; kurs tengah terjadi di Provinsi Jawa Tengah yang mencapai Rp13.609,38 per dolar Amerika pada&nbsp; minggu ketiga Oktober 2017.</p>', '2017-12-12 22:58:58.349', 'Perkembangan Nilai Tukar Eceran Rupiah Oktober 2017', '2017-12-29 11:07:07.41', '2', '0');
INSERT INTO "public"."news" VALUES ('4', 'lkajsdlkj<b><u><i><s>asdlkajsdlakjsd</s></i></u></b><p><span></span></p>', '2017-12-16 20:41:25.211', 'Baru', '2017-12-29 11:25:31.634', '2', '1');
INSERT INTO "public"."news" VALUES ('5', 'asdasd', '2017-12-25 11:57:34.917', 'qwd', '2017-12-26 20:13:08.175', '5', '1');
INSERT INTO "public"."news" VALUES ('6', '<p><span>asdasdasd</span></p>', '2017-12-26 18:00:50.156', 'asdasdasd', '2017-12-29 11:07:55.434', '5', '2');
INSERT INTO "public"."news" VALUES ('7', '<p><span>ljasdlkasd</span></p>', '2017-12-26 20:37:11.222', 'asdlkjasdlk', '2017-12-29 11:16:59.766', '5', '1');

-- ----------------------------
-- Table structure for news_approval
-- ----------------------------
DROP TABLE IF EXISTS "public"."news_approval";
CREATE TABLE "public"."news_approval" (
"id" int8 DEFAULT nextval('news_approval_id_seq'::regclass) NOT NULL,
"approvedat" timestamp(6),
"news_id" int8,
"publication_id" int8,
"approvedby" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of news_approval
-- ----------------------------
INSERT INTO "public"."news_approval" VALUES ('1', '2017-12-29 17:07:12.815', '5', null, '5');
INSERT INTO "public"."news_approval" VALUES ('2', '2017-12-29 17:10:57.508', '5', null, '5');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."permission";
CREATE TABLE "public"."permission" (
"id" int8 NOT NULL,
"description" varchar(255) COLLATE "default",
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO "public"."permission" VALUES ('225', 'view', 'publication.view');
INSERT INTO "public"."permission" VALUES ('226', 'create', 'publication.create');
INSERT INTO "public"."permission" VALUES ('227', 'update', 'publication.update');
INSERT INTO "public"."permission" VALUES ('228', 'delete', 'publication.delete');
INSERT INTO "public"."permission" VALUES ('229', 'view', 'news.view');
INSERT INTO "public"."permission" VALUES ('230', 'create', 'news.create');
INSERT INTO "public"."permission" VALUES ('231', 'update', 'news.update');
INSERT INTO "public"."permission" VALUES ('232', 'delete', 'news.delete');
INSERT INTO "public"."permission" VALUES ('233', 'view', 'account.view');
INSERT INTO "public"."permission" VALUES ('234', 'create', 'account.create');
INSERT INTO "public"."permission" VALUES ('235', 'update', 'account.update');
INSERT INTO "public"."permission" VALUES ('237', 'view', 'role.view');
INSERT INTO "public"."permission" VALUES ('238', 'create', 'role.create');
INSERT INTO "public"."permission" VALUES ('239', 'update', 'role.update');
INSERT INTO "public"."permission" VALUES ('240', 'delete', 'role.delete');
INSERT INTO "public"."permission" VALUES ('241', 'Configure Permission', 'permission.config');
INSERT INTO "public"."permission" VALUES ('242', 'create', 'category.create');
INSERT INTO "public"."permission" VALUES ('243', 'update', 'category.update');
INSERT INTO "public"."permission" VALUES ('244', 'delete', 'category.delete');
INSERT INTO "public"."permission" VALUES ('245', 'view', 'category.view');
INSERT INTO "public"."permission" VALUES ('246', 'view', 'approval.view');
INSERT INTO "public"."permission" VALUES ('247', 'news', 'approval.news');
INSERT INTO "public"."permission" VALUES ('248', 'publication', 'approval.publication');

-- ----------------------------
-- Table structure for publication
-- ----------------------------
DROP TABLE IF EXISTS "public"."publication";
CREATE TABLE "public"."publication" (
"id" int8 DEFAULT nextval('publication_id_seq'::regclass) NOT NULL,
"created" timestamp(6),
"description" text COLLATE "default",
"isnotice" bool NOT NULL,
"title" varchar(255) COLLATE "default" NOT NULL,
"updated" timestamp(6),
"account_id" int8 NOT NULL,
"content" text COLLATE "default",
"status" char(1) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of publication
-- ----------------------------
INSERT INTO "public"."publication" VALUES ('5', '2017-12-02 09:53:57.317657', 'asdfasdfasdf', 't', 'Maintenance Notice', '2017-12-02 09:53:57.317657', '1', null, '0');
INSERT INTO "public"."publication" VALUES ('6', '2017-12-02 09:53:57.317657', 'sdfsdfsdfsdfsdf', 'f', 'Changes in methods of calculating and announcing the Chinese', '2017-12-02 09:53:57.317657', '1', null, '0');
INSERT INTO "public"."publication" VALUES ('7', '2017-12-09 16:33:35.353', '<p><span>as<b><u><i>das</i></u></b>dasd</span></p>', 'f', 'lkjasdfsdf', '2017-12-09 16:33:35.353', '2', null, '2');
INSERT INTO "public"."publication" VALUES ('8', '2017-12-09 17:03:45.164', '<p><span></span>Go is a modern programming language built to deal with modern programming challenges, such as concurrency and compilation. Designed specifically with the web in mind, Go is an excellent language for writing web applications, specifically for web services. In his second book on Go, Mark Lewin will take you through serving, routing, connecting to a data source, using the templating engine, working with cookies, and more. Use Go Web Development Succinctly to build hugely scalable web applications with ease.</p>', 'f', 'Go Web Development', '2017-12-09 17:03:45.164', '2', null, '1');
INSERT INTO "public"."publication" VALUES ('9', '2017-12-09 17:06:29.639', '<p><span></span>Java is a high-level, cross-platform, object-oriented programming language that allows applications to be written once and run on a multitude of different devices. Java applications are ubiquitous, and the language is consistently ranked as one of the most popular and dominant in the world. Christopher Rose’s Java Succinctly Part 1 describes the foundations of Java–from printing a line of text to the console, to inheritance hierarchies in object-oriented programming. The e-book covers practical aspects of programming, such as debugging and using an IDE, as well as the core mechanics of the language.</p>', 'f', 'Java Succinctly Part 1', '2017-12-09 17:06:29.639', '2', null, '0');
INSERT INTO "public"."publication" VALUES ('10', '2017-12-09 17:07:02.29', '<p><span></span>Node.js is a wildly popular platform for writing web applications that has revolutionized web development in many ways, enjoying support across the open source community as well as industry. With Node.js Succinctly by Emanuele DelBono, you will learn the basics of Node.js: non-blocking I/O, the event loop, modules, and the Node.js runtime environment. From there, you will dive into building practical solutions that interact with filesystems and streams, access databases, handle web server message queuing, and more.</p>', 'f', 'Node.js Succinctly', '2017-12-09 17:07:02.29', '2', null, '1');
INSERT INTO "public"."publication" VALUES ('11', '2017-12-09 17:08:03.668', '<p><span></span>Developed by Facebook engineers, React is a JavaScript library that has revolutionized how developers design and think about views in web applications. It introduced a way for developers to declaratively describe user interfaces, and to model the state of these interfaces instead of the transactions on them. In React.js Succinctly, author Samer Buna introduces the novel approach to building user interfaces that React provides, and walks readers through the basics of declarative user interfaces, React components, working with user input, and more.</p>', 'f', 'React.js Succinctly', '2017-12-09 17:08:03.668', '2', null, '0');
INSERT INTO "public"."publication" VALUES ('12', '2017-12-25 12:05:57.179', 'asasda<b><u><i>sdas</i></u></b>dasd', 'f', 'asdasd', '2017-12-26 21:02:07.633', '5', null, '0');

-- ----------------------------
-- Table structure for publicationapproval
-- ----------------------------
DROP TABLE IF EXISTS "public"."publicationapproval";
CREATE TABLE "public"."publicationapproval" (
"id" int8 DEFAULT nextval('publicationapproval_id_seq'::regclass) NOT NULL,
"approvedat" timestamp(6),
"approvedby" int8,
"publication_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of publicationapproval
-- ----------------------------
INSERT INTO "public"."publicationapproval" VALUES ('1', '2017-12-29 17:16:52.285', '5', '8');
INSERT INTO "public"."publicationapproval" VALUES ('2', '2017-12-29 17:17:22.606', '5', '10');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS "public"."role";
CREATE TABLE "public"."role" (
"id" int8 DEFAULT nextval('role_id_seq'::regclass) NOT NULL,
"name" varchar(255) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO "public"."role" VALUES ('1', 'ROLE_EXECUTIVE');
INSERT INTO "public"."role" VALUES ('2', 'ROLE_ADMIN');
INSERT INTO "public"."role" VALUES ('3', 'ROLE_GUEST');
INSERT INTO "public"."role" VALUES ('4', 'ROLE_ANALYST');
INSERT INTO "public"."role" VALUES ('5', 'ROLE_MANAGER');
INSERT INTO "public"."role" VALUES ('6', 'ROLE_XXX');

-- ----------------------------
-- Table structure for role_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."role_account";
CREATE TABLE "public"."role_account" (
"account_id" int8 NOT NULL,
"role_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of role_account
-- ----------------------------
INSERT INTO "public"."role_account" VALUES ('1', '1');
INSERT INTO "public"."role_account" VALUES ('2', '1');
INSERT INTO "public"."role_account" VALUES ('2', '2');
INSERT INTO "public"."role_account" VALUES ('5', '1');
INSERT INTO "public"."role_account" VALUES ('5', '2');
INSERT INTO "public"."role_account" VALUES ('5', '3');
INSERT INTO "public"."role_account" VALUES ('5', '4');
INSERT INTO "public"."role_account" VALUES ('5', '5');
INSERT INTO "public"."role_account" VALUES ('5', '6');
INSERT INTO "public"."role_account" VALUES ('6', '3');
INSERT INTO "public"."role_account" VALUES ('7', '2');
INSERT INTO "public"."role_account" VALUES ('7', '3');
INSERT INTO "public"."role_account" VALUES ('8', '1');
INSERT INTO "public"."role_account" VALUES ('8', '3');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."role_permission";
CREATE TABLE "public"."role_permission" (
"role_id" int8 NOT NULL,
"permission_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO "public"."role_permission" VALUES ('1', '227');
INSERT INTO "public"."role_permission" VALUES ('2', '225');
INSERT INTO "public"."role_permission" VALUES ('2', '226');
INSERT INTO "public"."role_permission" VALUES ('2', '227');
INSERT INTO "public"."role_permission" VALUES ('2', '228');
INSERT INTO "public"."role_permission" VALUES ('2', '229');
INSERT INTO "public"."role_permission" VALUES ('2', '230');
INSERT INTO "public"."role_permission" VALUES ('2', '231');
INSERT INTO "public"."role_permission" VALUES ('2', '232');
INSERT INTO "public"."role_permission" VALUES ('2', '233');
INSERT INTO "public"."role_permission" VALUES ('2', '234');
INSERT INTO "public"."role_permission" VALUES ('2', '235');
INSERT INTO "public"."role_permission" VALUES ('2', '237');
INSERT INTO "public"."role_permission" VALUES ('2', '238');
INSERT INTO "public"."role_permission" VALUES ('2', '239');
INSERT INTO "public"."role_permission" VALUES ('2', '240');
INSERT INTO "public"."role_permission" VALUES ('2', '241');
INSERT INTO "public"."role_permission" VALUES ('2', '242');
INSERT INTO "public"."role_permission" VALUES ('2', '243');
INSERT INTO "public"."role_permission" VALUES ('2', '244');
INSERT INTO "public"."role_permission" VALUES ('2', '245');
INSERT INTO "public"."role_permission" VALUES ('2', '246');
INSERT INTO "public"."role_permission" VALUES ('2', '247');
INSERT INTO "public"."role_permission" VALUES ('2', '248');
INSERT INTO "public"."role_permission" VALUES ('3', '226');

-- ----------------------------
-- Table structure for variable
-- ----------------------------
DROP TABLE IF EXISTS "public"."variable";
CREATE TABLE "public"."variable" (
"id" int8 DEFAULT nextval('variable_id_seq1'::regclass) NOT NULL,
"name" varchar(255) COLLATE "default" NOT NULL,
"parent_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of variable
-- ----------------------------
INSERT INTO "public"."variable" VALUES ('1', 'Bank Notes and Coins in Circulation', null);
INSERT INTO "public"."variable" VALUES ('2', 'Monetary Base', null);

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "public"."account_id_seq" OWNED BY "account"."id";
ALTER SEQUENCE "public"."category_id_seq" OWNED BY "category"."id";
ALTER SEQUENCE "public"."data_flow_id_seq1" OWNED BY "data_flow"."id";
ALTER SEQUENCE "public"."data_set_id_seq1" OWNED BY "data_set"."id";
ALTER SEQUENCE "public"."news_approval_id_seq" OWNED BY "news_approval"."id";
ALTER SEQUENCE "public"."news_id_seq" OWNED BY "news"."id";
ALTER SEQUENCE "public"."publication_id_seq" OWNED BY "publication"."id";
ALTER SEQUENCE "public"."publicationapproval_id_seq" OWNED BY "publicationapproval"."id";
ALTER SEQUENCE "public"."role_id_seq" OWNED BY "role"."id";
ALTER SEQUENCE "public"."variable_id_seq1" OWNED BY "variable"."id";

-- ----------------------------
-- Uniques structure for table account
-- ----------------------------
ALTER TABLE "public"."account" ADD UNIQUE ("email");

-- ----------------------------
-- Primary Key structure for table account
-- ----------------------------
ALTER TABLE "public"."account" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table category
-- ----------------------------
ALTER TABLE "public"."category" ADD UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table category
-- ----------------------------
ALTER TABLE "public"."category" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table category_data_flow
-- ----------------------------
ALTER TABLE "public"."category_data_flow" ADD PRIMARY KEY ("data_flow_id", "category_id");

-- ----------------------------
-- Primary Key structure for table data_flow
-- ----------------------------
ALTER TABLE "public"."data_flow" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table data_flow_item
-- ----------------------------
ALTER TABLE "public"."data_flow_item" ADD PRIMARY KEY ("data_flow_id", "variable_id");

-- ----------------------------
-- Primary Key structure for table data_set
-- ----------------------------
ALTER TABLE "public"."data_set" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table news
-- ----------------------------
ALTER TABLE "public"."news" ADD UNIQUE ("title");

-- ----------------------------
-- Primary Key structure for table news
-- ----------------------------
ALTER TABLE "public"."news" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table news_approval
-- ----------------------------
ALTER TABLE "public"."news_approval" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table publication
-- ----------------------------
ALTER TABLE "public"."publication" ADD UNIQUE ("title");

-- ----------------------------
-- Primary Key structure for table publication
-- ----------------------------
ALTER TABLE "public"."publication" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table publicationapproval
-- ----------------------------
ALTER TABLE "public"."publicationapproval" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table role_account
-- ----------------------------
ALTER TABLE "public"."role_account" ADD PRIMARY KEY ("account_id", "role_id");

-- ----------------------------
-- Primary Key structure for table role_permission
-- ----------------------------
ALTER TABLE "public"."role_permission" ADD PRIMARY KEY ("role_id", "permission_id");

-- ----------------------------
-- Uniques structure for table variable
-- ----------------------------
ALTER TABLE "public"."variable" ADD UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table variable
-- ----------------------------
ALTER TABLE "public"."variable" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "public"."category"
-- ----------------------------
ALTER TABLE "public"."category" ADD FOREIGN KEY ("parent_id") REFERENCES "public"."category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."category_data_flow"
-- ----------------------------
ALTER TABLE "public"."category_data_flow" ADD FOREIGN KEY ("data_flow_id") REFERENCES "public"."data_flow" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."category_data_flow" ADD FOREIGN KEY ("category_id") REFERENCES "public"."category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."category_news"
-- ----------------------------
ALTER TABLE "public"."category_news" ADD FOREIGN KEY ("category_id") REFERENCES "public"."news" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."category_news" ADD FOREIGN KEY ("news_id") REFERENCES "public"."category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."category_publication"
-- ----------------------------
ALTER TABLE "public"."category_publication" ADD FOREIGN KEY ("publication_id") REFERENCES "public"."category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."category_publication" ADD FOREIGN KEY ("category_id") REFERENCES "public"."publication" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."data_flow_item"
-- ----------------------------
ALTER TABLE "public"."data_flow_item" ADD FOREIGN KEY ("data_flow_id") REFERENCES "public"."data_flow" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."data_flow_item" ADD FOREIGN KEY ("variable_id") REFERENCES "public"."variable" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."data_set"
-- ----------------------------
ALTER TABLE "public"."data_set" ADD FOREIGN KEY ("variable_id") REFERENCES "public"."variable" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."news"
-- ----------------------------
ALTER TABLE "public"."news" ADD FOREIGN KEY ("account_id") REFERENCES "public"."account" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."news_approval"
-- ----------------------------
ALTER TABLE "public"."news_approval" ADD FOREIGN KEY ("news_id") REFERENCES "public"."news" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."news_approval" ADD FOREIGN KEY ("publication_id") REFERENCES "public"."publication" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."news_approval" ADD FOREIGN KEY ("approvedby") REFERENCES "public"."account" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."publication"
-- ----------------------------
ALTER TABLE "public"."publication" ADD FOREIGN KEY ("account_id") REFERENCES "public"."account" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."publicationapproval"
-- ----------------------------
ALTER TABLE "public"."publicationapproval" ADD FOREIGN KEY ("publication_id") REFERENCES "public"."publication" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."publicationapproval" ADD FOREIGN KEY ("approvedby") REFERENCES "public"."account" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."role_account"
-- ----------------------------
ALTER TABLE "public"."role_account" ADD FOREIGN KEY ("account_id") REFERENCES "public"."account" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."role_account" ADD FOREIGN KEY ("role_id") REFERENCES "public"."role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."role_permission"
-- ----------------------------
ALTER TABLE "public"."role_permission" ADD FOREIGN KEY ("permission_id") REFERENCES "public"."permission" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."role_permission" ADD FOREIGN KEY ("role_id") REFERENCES "public"."role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."variable"
-- ----------------------------
ALTER TABLE "public"."variable" ADD FOREIGN KEY ("parent_id") REFERENCES "public"."variable" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
