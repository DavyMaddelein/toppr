-- MySQL dump 10.13  Distrib 5.1.45, for Win64 (unknown)
--
-- Host: localhost    Database: ppr
-- ------------------------------------------------------
-- Server version	5.1.45-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cell_source`
--

DROP TABLE IF EXISTS `cell_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cell_source` (
  `cell_sourceid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `l_origin` int(10) NOT NULL,
  `l_taxonomy` int(10) NOT NULL,
  `organ` text,
  `celltype` varchar(80) DEFAULT NULL,
  `subcellular_location` text,
  `developmental_stage` text,
  `sex` enum('null','male','female') DEFAULT NULL,
  `disease_state` text,
  `permanent_transfection` text,
  `source` text,
  `pre_treatment` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`cell_sourceid`),
  KEY `origin` (`l_origin`),
  KEY `taxonomy` (`l_taxonomy`),
  KEY `Index_4` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cell_source`
--

LOCK TABLES `cell_source` WRITE;
/*!40000 ALTER TABLE `cell_source` DISABLE KEYS */;
INSERT INTO `cell_source` VALUES (1,'Jurkat',1,9606,'','','','','female','','','','','niklaas@%','2008-02-21 15:24:11','2008-03-10 09:35:05'),(2,'A549',1,9606,'lung - BTO:0000763','epithelial cell of lung - CL:0000082','','','male','Cancer of Lung - DOID:3905','','','','niklaas@%','2008-03-19 16:13:44','2008-04-11 15:46:47'),(3,'Mf4/4',1,10090,'','','','','null','','','','','niklaas@%','2008-04-02 14:21:12','2008-04-02 14:21:12'),(4,'Huh-7.5',1,9606,'','','','','null','','','','','veerle@%','2008-04-23 16:27:08','2008-04-23 16:27:08'),(5,'YAC-1',1,10090,'','lymphoblast - CL:0000229','','','null','','','ATCC-number: TIB-160','pan-caspase inhibitors z-VAD-fmk (50 µM f.c.) and z-DEVD-fmk (50 µM f.c.)','petra@%','2008-04-28 14:54:48','2008-04-28 14:54:48'),(6,'SH-SY5Y',1,9606,'brain [BTO:0000142] - BTO:0000142','neuroblast [CL:0000031] - CL:0000031','','','null','Neuroblastoma - DOID:769','','ATCC','','francis@%','2008-05-06 09:51:45','2008-05-06 09:51:45'),(7,'HACAT',1,9606,'skin - BTO:0001253','keratinocyte - CL:0000312','','','null','','','','','petra@%','2008-05-26 15:24:57','2008-05-26 15:24:57'),(8,'NB4',1,9606,'bone marrow cell line - BTO:0000264','fibroblast - CL:0000057','','','null','AML - Acute Myeloid Leukemia - DOID:9119','','Dr. M. Lanotte (Hopital St Louis, Paris, France)','','petra@%','2008-05-27 15:10:06','2008-05-27 15:29:59'),(9,'K-562',1,9606,'bone marrow - BTO:0000141','lymphoblast - CL:0000229','','','null','Chronic myeloid leukaemia - DOID:8552','','ATCC - CCL243','','petra@%','2008-07-12 22:52:49','2008-07-12 22:52:49'),(10,'human blood platelets',2,9606,'megakaryocyte - BTO:0000843','blood platelet - CL:0000233','','','null','','','healthy donor buffy coats','isolation and wash of platelets in special buffer','francis@%','2008-08-08 10:07:42','2008-08-08 10:07:42'),(11,'MEF Bax/Bak DKO cells',1,10090,'embryonic fibroblast cell line - BTO:0001958','fibroblast - CL:0000057','','','null','','Bak/Bak double knockout','Boris Turk (under MTA)','','francis@%','2008-11-24 17:29:33','2008-11-24 17:29:33'),(12,'human hepatocyte',3,10090,'liver - BTO:0000759','','','','null','','','','','veerle@%','2009-04-07 14:19:33','2009-04-07 14:19:33'),(13,'mouse dendritic cells',2,10090,'dendritic cell - BTO:0002042','dendritic cell - CL:0000451','','','female','','','','','francis@%','2009-07-01 17:32:46','2009-07-01 17:32:46');
/*!40000 ALTER TABLE `cell_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cofradic`
--

DROP TABLE IF EXISTS `cofradic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cofradic` (
  `cofradicid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type_2` varchar(50) NOT NULL DEFAULT '',
  `description` text,
  `username` varchar(45) NOT NULL DEFAULT 'CONVERSION_FROM_MS_LIMS_4',
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`cofradicid`),
  UNIQUE KEY `type` (`type_2`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cofradic`
--

LOCK TABLES `cofradic` WRITE;
/*!40000 ALTER TABLE `cofradic` DISABLE KEYS */;
INSERT INTO `cofradic` VALUES (1,'MetOx','Methionine oxidation COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(2,'Cys','Cysteine COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(3,'Nterm','N-terminal COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(4,'Phospho','Phospho-COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(5,'none','No COFRADIC(TM) chemistry used','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(6,'Nitro-Tyr','Nitro-tyrosine COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(7,'FSBA','FSBA COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(8,'N-glyco','Asn glycosylation COFRADIC','CONVERSION_FROM_MS_LIMS_4','2006-01-04 20:04:16','2006-01-04 20:04:16'),(9,'Cterm','C-terminal COFRADIC','CONVERSION_FROM_MS_LIMS_4','2007-03-30 12:09:08','2007-03-30 12:09:08');
/*!40000 ALTER TABLE `cofradic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain` (
  `domainid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_proteinid` int(10) unsigned DEFAULT NULL,
  `name` text,
  `type` varchar(10) DEFAULT NULL,
  `domaintermid` text,
  `start` int(10) DEFAULT NULL,
  `end` int(10) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`domainid`)
) ENGINE=MyISAM AUTO_INCREMENT=25477 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `experiment`
--

DROP TABLE IF EXISTS `experiment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experiment` (
  `experimentid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`experimentid`),
  KEY `Index_2` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiment`
--

LOCK TABLES `experiment` WRITE;
/*!40000 ALTER TABLE `experiment` DISABLE KEYS */;
INSERT INTO `experiment` VALUES (1,'Cell culture','','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58'),(2,'Cell free','','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58'),(3,'Model organism','','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58'),(4,'In vivo','','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58');
/*!40000 ALTER TABLE `experiment` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `fragmention`
--

DROP TABLE IF EXISTS `fragmention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fragmention` (
  `fragmentionid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_peptideid` int(10) unsigned NOT NULL DEFAULT '0',
  `iontype` int(10) unsigned NOT NULL DEFAULT '0',
  `ionname` varchar(45) NOT NULL DEFAULT '',
  `l_ionscoringid` int(10) unsigned NOT NULL DEFAULT '0',
  `mz` decimal(12,4) NOT NULL DEFAULT '0.0000',
  `intensity` int(10) unsigned DEFAULT NULL,
  `fragmentionnumber` int(10) unsigned DEFAULT NULL,
  `massdelta` decimal(12,4) DEFAULT NULL,
  `masserrormargin` decimal(12,4) NOT NULL DEFAULT '0.0000',
  `username` varchar(45) NOT NULL DEFAULT '',
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`fragmentionid`),
  KEY `l_ionscoringid index` (`l_ionscoringid`),
  KEY `iontype index` (`iontype`) USING BTREE,
  KEY `l_identificationid index` (`l_peptideid`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=497581 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `go`
--

DROP TABLE IF EXISTS `go`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `go` (
  `goid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_proteinid` int(10) unsigned DEFAULT NULL,
  `name` text,
  `type` varchar(5) DEFAULT NULL,
  `gotermid` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`goid`)
) ENGINE=MyISAM AUTO_INCREMENT=19742 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groupusers`
--

DROP TABLE IF EXISTS `groupusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupusers` (
  `groupusersid` int(11) NOT NULL AUTO_INCREMENT,
  `groupusersname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`groupusersid`),
  KEY `Index_2` (`groupusersname`),
  KEY `Index_3` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `homologene_accession`
--

DROP TABLE IF EXISTS `homologene_accession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homologene_accession` (
  `homoloGene_accessionid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_proteinid` int(10) unsigned NOT NULL,
  `homoloGeneid` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`homoloGene_accessionid`),
  KEY `Index_2` (`l_proteinid`)
) ENGINE=MyISAM AUTO_INCREMENT=4657 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inhibitor`
--

DROP TABLE IF EXISTS `inhibitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inhibitor` (
  `inhibitorid` int(10) NOT NULL AUTO_INCREMENT,
  `scientific_name` varchar(50) NOT NULL DEFAULT '',
  `common_name` varchar(50) DEFAULT '',
  `meropsid` varchar(15) DEFAULT '',
  `source` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`inhibitorid`),
  KEY `Index_2` (`scientific_name`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `instrument`
--

DROP TABLE IF EXISTS `instrument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instrument` (
  `instrumentid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `description` text,
  `username` varchar(45) NOT NULL DEFAULT '',
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`instrumentid`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ionscoring`
--

DROP TABLE IF EXISTS `ionscoring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ionscoring` (
  `ionscoringid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL DEFAULT '',
  `username` varchar(45) NOT NULL DEFAULT '',
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ionscoringid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ms_lims_project`
--

DROP TABLE IF EXISTS `ms_lims_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ms_lims_project` (
  `ms_lims_projectid` int(10) NOT NULL AUTO_INCREMENT,
  `ms_limstitle` varchar(250) NOT NULL DEFAULT '',
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`ms_lims_projectid`)
) ENGINE=MyISAM AUTO_INCREMENT=678 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `origin`
--

DROP TABLE IF EXISTS `origin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `origin` (
  `originid` int(10) NOT NULL AUTO_INCREMENT,
  `origin` text NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`originid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `origin`
--

LOCK TABLES `origin` WRITE;
/*!40000 ALTER TABLE `experiment` DISABLE KEYS */;
INSERT INTO `origin` VALUES (1,'Cell line','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58'),(2,'Primary cell line','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58'),(3,'Tissue','niklaas@%','2008-02-15 11:08:58','2008-02-15 11:08:58');
/*!40000 ALTER TABLE `experiment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orthologue`
--

DROP TABLE IF EXISTS `orthologue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orthologue` (
  `orthologueid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_proteinid` int(10) unsigned DEFAULT NULL,
  `entry_name` varchar(15) DEFAULT NULL,
  `sequence` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`orthologueid`),
  KEY `Index_2` (`l_proteinid`),
  KEY `Index_3` (`entry_name`)
) ENGINE=MyISAM AUTO_INCREMENT=24686 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pdb`
--

DROP TABLE IF EXISTS `pdb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pdb` (
  `pdbid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_proteinid` int(10) unsigned DEFAULT NULL,
  `pdbaccession` varchar(10) DEFAULT NULL,
  `title` text,
  `experiment_type` varchar(45) DEFAULT NULL,
  `resolution` varchar(10) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`pdbid`)
) ENGINE=MyISAM AUTO_INCREMENT=3812 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pdb_block`
--

DROP TABLE IF EXISTS `pdb_block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pdb_block` (
  `pdb_blockid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_pdbid` int(10) unsigned DEFAULT NULL,
  `l_proteinid` int(10) unsigned DEFAULT NULL,
  `block` varchar(5) DEFAULT NULL,
  `start_protein` int(10) DEFAULT NULL,
  `end_protein` int(10) DEFAULT NULL,
  `start_block` int(10) DEFAULT NULL,
  `end_block` int(10) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`pdb_blockid`)
) ENGINE=MyISAM AUTO_INCREMENT=6788 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `peptide`
--

DROP TABLE IF EXISTS `peptide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peptide` (
  `identificationid` int(10) unsigned NOT NULL,
  `l_groupid` int(10) unsigned DEFAULT NULL,
  `l_projectid` int(10) unsigned NOT NULL,
  `l_instrumentid` int(10) unsigned NOT NULL,
  `modified_sequence` text,
  `ion_coverage` text,
  `score` int(10) unsigned NOT NULL DEFAULT '0',
  `identitythreshold` int(50) unsigned NOT NULL DEFAULT '0',
  `confidence` decimal(12,4) NOT NULL DEFAULT '0.0500',
  `precursor` decimal(12,4) NOT NULL DEFAULT '0.0000',
  `charge` smallint(50) unsigned NOT NULL DEFAULT '0',
  `exp_mass` decimal(12,4) NOT NULL DEFAULT '0.0000',
  `cal_mass` decimal(12,4) NOT NULL DEFAULT '0.0000',
  `spectrumfile` longblob,
  `db` varchar(50) NOT NULL,
  `mascot_version` varchar(25) NOT NULL,
  `identificationdate` datetime DEFAULT '0000-00-00 00:00:00',
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`identificationid`),
  KEY `l_groupid` (`l_groupid`),
  KEY `l_projectid` (`l_projectid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `peptide_group`
--

DROP TABLE IF EXISTS `peptide_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peptide_group` (
  `groupid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_taxonomy` int(10) NOT NULL,
  `peptide_sequence` varchar(150) NOT NULL,
  `mapped` tinyint(1) NOT NULL,
  `old` tinyint(1) NOT NULL,
  `position` varchar(1) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`groupid`) USING BTREE,
  KEY `taxonomy` (`l_taxonomy`),
  KEY `Index_3` (`peptide_sequence`)
) ENGINE=MyISAM AUTO_INCREMENT=8213 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `peptide_location`
--

DROP TABLE IF EXISTS `peptide_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peptide_location` (
  `peptide_locationid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_proteinid` int(10) unsigned DEFAULT NULL,
  `l_groupid` int(10) unsigned NOT NULL,
  `start` int(10) unsigned DEFAULT NULL,
  `end` int(10) unsigned DEFAULT NULL,
  `location` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`peptide_locationid`),
  KEY `protein` (`l_proteinid`),
  KEY `group` (`l_groupid`)
) ENGINE=MyISAM AUTO_INCREMENT=9182 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `peptide_treatment_and_inhibitor`
--

DROP TABLE IF EXISTS `peptide_treatment_and_inhibitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peptide_treatment_and_inhibitor` (
  `peptide_treatment_and_inhibitorid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_inhibitorid` int(10) unsigned NOT NULL,
  `l_treatmentid` int(10) unsigned NOT NULL,
  `l_identificationid` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`peptide_treatment_and_inhibitorid`),
  KEY `inhibitor` (`l_inhibitorid`),
  KEY `treatment` (`l_treatmentid`),
  KEY `identification` (`l_identificationid`)
) ENGINE=MyISAM AUTO_INCREMENT=30001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `projectid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `l_cell_sourceid` int(10) unsigned NOT NULL,
  `l_experimentid` int(10) unsigned NOT NULL,
  `l_cofradicid` int(10) unsigned NOT NULL,
  `l_userid` int(10) unsigned NOT NULL,
  `title` varchar(250) NOT NULL,
  `description` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`projectid`),
  KEY `cell_source` (`l_cell_sourceid`),
  KEY `experiment` (`l_experimentid`),
  KEY `cofradic` (`l_cofradicid`),
  KEY `user` (`l_userid`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_to_ms_lims_project`
--

DROP TABLE IF EXISTS `project_to_ms_lims_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_to_ms_lims_project` (
  `project_to_ms_lims_projectid` int(10) NOT NULL AUTO_INCREMENT,
  `l_projectid` int(10) NOT NULL,
  `l_ms_limsprojectid` int(10) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`project_to_ms_lims_projectid`)
) ENGINE=MyISAM AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_to_usergroup`
--

DROP TABLE IF EXISTS `project_to_usergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_to_usergroup` (
  `project_to_usergroupid` int(11) NOT NULL AUTO_INCREMENT,
  `l_projectid` int(10) NOT NULL,
  `l_usergroupid` int(10) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`project_to_usergroupid`),
  KEY `Index_2` (`l_projectid`),
  KEY `Index_3` (`l_usergroupid`)
) ENGINE=MyISAM AUTO_INCREMENT=125 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `protein`
--

DROP TABLE IF EXISTS `protein`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `protein` (
  `proteinid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SPaccession` varchar(50) NOT NULL,
  `sequence` text,
  `entry_name` varchar(100) NOT NULL,
  `name` text,
  `sec_structure_prediction` text,
  `sec_structure_swissprot` text,
  `info_found` tinyint(4) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`proteinid`),
  KEY `Index_2` (`SPaccession`),
  KEY `Index_3` (`entry_name`)
) ENGINE=MyISAM AUTO_INCREMENT=3275 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `taxonomy`
--

DROP TABLE IF EXISTS `taxonomy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taxonomy` (
  `taxonomyid` int(10) NOT NULL,
  `species` varchar(100) NOT NULL,
  `scientific_name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`taxonomyid`),
  KEY `Index_2` (`species`),
  KEY `Index_3` (`scientific_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxonomy`
--

LOCK TABLES `taxonomy` WRITE;
/*!40000 ALTER TABLE `taxonomy` DISABLE KEYS */;
INSERT INTO `taxonomy` VALUES (0,'NONE','NONE','niklaas@%','2008-02-15 11:14:23','2008-02-15 11:14:23'),(9606,'Human','Homo sapiens','niklaas@%','2008-02-15 11:14:23','2008-02-15 11:14:23'),(10090,'house mouse','Mus musculus','niklaas@%','2008-02-15 11:14:23','2008-02-15 11:14:23'),(10116,'Norway rat','Rattus norvegicus','niklaas@%','2008-02-15 11:14:23','2008-02-15 11:14:23'),(11676,'HIV-1','human immunodeficiency virus type I HIV-1','francis@%','2008-04-28 11:12:31','2008-04-28 11:12:31'),(388904,'HIV-1','HIV-1 [388904]','francis@%','2008-04-28 11:16:15','2008-04-28 11:16:15'),(11103,'hepatitis C virus','hepatitis C virus','veerle@%','2009-04-03 13:02:45','2009-04-03 13:02:45');
/*!40000 ALTER TABLE `taxonomy` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `treatment`
--

DROP TABLE IF EXISTS `treatment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `treatment` (
  `treatmentid` int(10) NOT NULL AUTO_INCREMENT,
  `scientific_name` varchar(50) NOT NULL DEFAULT '',
  `common_name` varchar(50) DEFAULT '',
  `protease` enum('yes','no') DEFAULT NULL,
  `recombinant` enum('yes','no') DEFAULT NULL,
  `l_taxonomy` int(10) DEFAULT NULL,
  `meropsid` varchar(15) DEFAULT '',
  `source` text,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`treatmentid`),
  KEY `taxonomy` (`l_taxonomy`),
  KEY `Index_3` (`scientific_name`),
  KEY `Index_4` (`common_name`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificationdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`userid`),
  KEY `Index_2` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroup` (
  `usergroupid` int(11) NOT NULL AUTO_INCREMENT,
  `usergroupname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`usergroupid`),
  KEY `Index_2` (`usergroupname`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usergroup_to_groupusers`
--

DROP TABLE IF EXISTS `usergroup_to_groupusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroup_to_groupusers` (
  `usergroup_to_groupusersid` int(11) NOT NULL AUTO_INCREMENT,
  `l_usergroupid` int(10) NOT NULL,
  `l_groupusersid` int(10) NOT NULL,
  `username` varchar(45) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`usergroup_to_groupusersid`),
  KEY `Index_2` (`l_usergroupid`),
  KEY `Index_3` (`l_groupusersid`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-06-15  8:07:19
