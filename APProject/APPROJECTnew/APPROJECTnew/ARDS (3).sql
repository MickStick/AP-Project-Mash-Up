-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 12, 2017 at 05:28 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ARDS`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `Emp_ID` int(11) NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `onlineStatus` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`Emp_ID`, `firstName`, `lastName`, `email`, `onlineStatus`) VALUES
(4000000, 'Kakashi', 'Sama', 'ksama@yahoo.com', 1),
(4001001, 'Nemo', 'Found', 'nfound@yes.com', 0),
(4001010, 'Prince', 'Vegeta', 'pvegeta@dbz.com', 0),
(4002002, 'Akira', 'Omino', 'aomino@yahoo.com', 0),
(4002020, 'Bilal', 'Ahmed', 'bahmed@gmail.com', 0),
(4003003, 'Raika', 'Onimo', 'ronimo@live.com', 0),
(4003030, 'Orandi', 'Harris', 'oharris@death.com', 1),
(4004004, 'Justin', 'Grey', 'jgrey@ras.com', 0),
(4004040, 'Mikhail', 'Shaw', 'mshaw@happy.com', 0),
(4005005, 'Moses', 'Water', 'mwater@split.com', 0),
(4005050, 'Tyrone', 'Edwards', 'tedwards@pass.com', 1),
(4006006, 'Kendrick', 'Afar', 'kafar@live.com', 0),
(4006060, 'David', 'White', 'dwhite@white.com', 0),
(4007007, 'Trudy', 'Douglas', 'tdouglas@yahoo.com', 0),
(4007070, 'Whiteor', 'Black', 'whiteorblack@grey.com', 0),
(4008008, 'Bill', 'Walls', 'bwalls@live.com', 1),
(4008080, 'Jermaine', 'Hot', 'jhot@cole.com', 0),
(4009009, 'Tsunade', 'Senpai', 'tsenpai@konoha.com', 0),
(4009090, 'Vanessa', 'Hutch', 'vhutch@live.com', 0),
(4010000, 'Serpentine', 'Sniper', 'ssniper@dodge.com', 0);

-- --------------------------------------------------------

--
-- Table structure for table `employee_enquiry`
--

CREATE TABLE `employee_enquiry` (
  `emp_ID` int(11) NOT NULL,
  `ticket_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_enquiry`
--

INSERT INTO `employee_enquiry` (`emp_ID`, `ticket_ID`) VALUES
(4002002, 300002),
(4004040, 300002),
(4002020, 300008),
(4005050, 300012),
(4001001, 300013);

-- --------------------------------------------------------

--
-- Table structure for table `employee_password`
--

CREATE TABLE `employee_password` (
  `emp_ID` int(11) NOT NULL,
  `password` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_password`
--

INSERT INTO `employee_password` (`emp_ID`, `password`) VALUES
(4000000, '6967a3906c4d302050f7e8b7359af270feac3bf7139da7962881978ac11c6530'),
(4001001, '3e53450179c851d86f85f8df494ba0cbc3fac9393937046af8e88fe15976a50b'),
(4001010, 'cf3f8e2faabfa7f955263cc97a6467b57cb91819686ecf9850a66660285ccd6d'),
(4002002, 'd7fa46bdb08730161b1a96035032475708638c11c12d85636f0918038c7dfb46'),
(4002020, '06838ef359be843d481b5b70aa59bfdd7c9136dc3773b8430ea2aa62184a1f58'),
(4003003, 'a9672c51146670ffe1c01b4b10d6c9886d8f73f9a5008f7cd3287639d591ec22'),
(4003030, 'a15d7c604ac1832c2401951683af0a4e04b76f07a1353a8ae0cf8d637d4b8dd1'),
(4004004, 'dae82ff802998410bd64551a413ee65643648d3baf6449d5575f68bde6325cc5'),
(4004040, '7f93a8233cb2bb5778680e467bde36894ef25d042a9f98ee23a3bddf6c4adfbc'),
(4005005, '782bc3004c7513c43e4a3c8bdb38087e8289e32e0ab2d9f50152c8a93d79a201'),
(4005050, 'a058a5c3469094df590a864841c6c03bfeeb9800b5acb6b28f3d1bebd063d7b2'),
(4006006, '2fd912485d0c30da1a7e2675327cee91fe42d14e7ca969f5b562da133cc1079e'),
(4006060, 'df83b0e6c26a96d9fec945b2cc57bf52fc3629fd463904c7f2227fd6778c4537'),
(4007007, '426f091201265499b55c2d7af360fc05f028f73473017942349d0ff884259cc2'),
(4007070, 'e566e8e02b44f31c272e4f0d85c605cc32d41013c230853bf0c0ff0747c74600'),
(4008008, '6ee2c13f516e46f26f1a32f9dd3d8077ac05542a9208370c7eb5d80e61e8f291'),
(4008080, 'ab48c3700608a212f458cb1d04a24f4f6fa99e10cd8556184038880a25016706'),
(4009009, 'b0fc001e604141a90fa1f0a108d2a47015f20fdd0af9faf24d9cc21bdddac4af'),
(4009090, 'd04cd2c38e0d4b63c1f6fbd248d9d8b9b8c826416767299d8ce6ab55f6dab9b3'),
(4010000, '9a72f4d3ba4401e5cbf6575b140702e42edc43c5f1101504f4389b3472104e54');

-- --------------------------------------------------------

--
-- Table structure for table `enquiry`
--

CREATE TABLE `enquiry` (
  `ticket_ID` int(11) NOT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateResponded` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `message` varchar(140) DEFAULT NULL,
  `type` set('1','2','3','4','5') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `enquiry`
--

INSERT INTO `enquiry` (`ticket_ID`, `dateCreated`, `dateResponded`, `status`, `message`, `type`) VALUES
(300001, '2017-03-16', NULL, 'Open', 'I want to withdrawn from a module but I cannot do so online.', ''),
(300002, '2017-03-27', '2017-03-28', 'Closed', 'My semester fee breakdown is not correct.', ''),
(300008, '2017-02-01', '2017-02-10', 'Closed', 'I want to apply for a refund for my tuition.', ''),
(300012, '2017-03-01', '2017-03-07', 'Closed', 'Need to understand why my account has a negative balance.', ''),
(300013, '2017-03-06', '2017-03-10', 'Closed', 'I have paid my full tuition but I have not been give financial clearance.', '');

-- --------------------------------------------------------

--
-- Table structure for table `FeePayment`
--

CREATE TABLE `FeePayment` (
  `fee_ID` int(20) NOT NULL,
  `student_ID` int(7) NOT NULL,
  `amount` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FeePayment`
--

INSERT INTO `FeePayment` (`fee_ID`, `student_ID`, `amount`) VALUES
(10010, 1407007, 32000),
(10015, 1409009, 23000),
(10020, 1406060, 45000),
(10023, 1401001, 30000),
(10035, 1406006, 15000),
(10067, 1401010, 65000),
(10089, 1402002, 90000),
(10100, 1403003, 100000),
(10400, 1401030, 60000),
(10600, 1408080, 40000);

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `module_ID` int(10) NOT NULL,
  `module_Name` varchar(50) NOT NULL,
  `level` int(9) NOT NULL,
  `credits` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`module_ID`, `module_Name`, `level`, `credits`) VALUES
(1001, 'Information Technology', 1, 3),
(1005, 'Computer Networks', 1, 3),
(1008, 'Programming I', 1, 3),
(1009, 'Computer Research Methods', 3, 3),
(1019, 'Physics', 2, 3),
(2001, 'Environmental Studies', 1, 3),
(2004, 'Academic Writing', 2, 3),
(2019, 'Data Structures', 2, 3),
(3004, 'Software Implementation', 3, 3),
(3006, 'IS Innovation', 3, 3),
(3007, 'Operating Systems', 3, 3),
(3010, 'Professional Ethics', 2, 3),
(3012, 'Theory Of Computation', 3, 3),
(3021, 'Foundation of Information Systems', 3, 3),
(4003, 'Advanced Programming', 3, 4),
(4014, 'Database Administration', 3, 4),
(6001, 'Computer Security', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `module_level`
--

CREATE TABLE `module_level` (
  `level` int(11) NOT NULL,
  `cost` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `module_level`
--

INSERT INTO `module_level` (`level`, `cost`) VALUES
(1, 10000),
(2, 20000),
(3, 30000),
(4, 40000);

-- --------------------------------------------------------

--
-- Table structure for table `Owed_Payment`
--

CREATE TABLE `Owed_Payment` (
  `stud_ID` int(11) NOT NULL,
  `amountOwed` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Owed_Payment`
--

INSERT INTO `Owed_Payment` (`stud_ID`, `amountOwed`) VALUES
(1401001, -20000),
(1401010, 30000),
(1404040, -75000),
(1405050, -35000),
(1401020, -70000),
(1401030, 10000),
(1402002, -5000),
(1404004, 2000),
(1408008, -15000),
(1407070, 40000),
(1409090, -234000),
(1406006, 0),
(1404040, -75000),
(1405050, -35000),
(1401020, -70000),
(1401030, 10000),
(1402002, -5000),
(1404004, 2000),
(1408008, -15000),
(1407070, 40000),
(1409090, -234000),
(1406006, 0);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `stud_ID` int(7) NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `accountBal` float DEFAULT NULL,
  `telephoneNum` varchar(13) DEFAULT NULL,
  `clearance_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`stud_ID`, `firstName`, `lastName`, `email`, `accountBal`, `telephoneNum`, `clearance_status`) VALUES
(1401001, 'Britney', 'Coone', 'britneycoone@gmail.com', 5000, '1(876)6782937', 'cleared'),
(1401010, 'William', 'Chen', 'williamchen@gmail.com', 250000, '1(876)1234567', 'cleared'),
(1401020, 'Goku', 'Sama', 'gokusama@gmail.com', 50000, '1(876)9098080', 'cleared'),
(1401030, 'Naruto', 'Uzumaki', 'narutouzumaki@yahoo.com', 0, '1(876)5323845', 'not cleared'),
(1402002, 'Lee', 'Jones', 'leejones@yahoo.com', 67000, '1(876)9873564', 'not cleared'),
(1403003, 'Jessica', 'James', 'jessicajames@gmail.com', 98000, '1(876)6475837', 'not cleared'),
(1404004, 'Leanna', 'Dimester', 'leannadimester@yahoo.com', 95000, '1(876)4675938', 'cleared'),
(1404040, 'Itachi', 'Uchiha', 'itachiuchiha@killmyfam.com', 500000, '1(876)5673488', 'cleared'),
(1405005, 'Dexter', 'Deedee', 'dexterdeedee@lab.com', 0, '1(876)5437594', 'cleared'),
(1405050, 'Sakura', 'Something', 'sakura@pink.com', 25000, '1(876)4560998', 'not cleared'),
(1406006, 'Jak', 'Daxter', 'jakdaxter@oracle.com', 9, '1(876)1357975', 'cleared'),
(1406060, 'John', 'Doe', 'johndoe@live.com', 50000, '1(876)9873564', 'not cleared'),
(1406949, 'Wembley ', 'Williams', 'wembleywilliams@hello.com', 0, '1(876)9876789', ''),
(1406952, 'David', 'Rhoden', 'davidrhoden@live.com', 25000, '1(876)4566543', ''),
(1407007, 'Jon', 'Snow', 'jonsnow@unknown.com', 1500000, '1(876)9087656', ''),
(1407070, 'Alex', 'Smith', 'alexsmith@lexi.com', 0, '1(876)3749279', ''),
(1408008, 'Beyonce', 'Knows', 'beyonceknows@lemonade.com', 0, '1(876)8888888', ''),
(1408080, 'Adrian', 'Matthews', 'adrianmatthews@live.com', 500, '1(876)3458293', ''),
(1409009, 'Rihanna', 'Diamond', 'rihannadiamond@shine.com', 0, '1(876)2050205', ''),
(1409090, 'Jillian', 'James', 'jillianjames@live.com', 250, '1(876)4567893', '');

-- --------------------------------------------------------

--
-- Table structure for table `student_enquiry`
--

CREATE TABLE `student_enquiry` (
  `stud_ID` int(11) NOT NULL,
  `ticket_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_enquiry`
--

INSERT INTO `student_enquiry` (`stud_ID`, `ticket_ID`) VALUES
(1401001, 300001),
(1404040, 300008),
(1401020, 300012),
(1401030, 300013);

-- --------------------------------------------------------

--
-- Table structure for table `student_module`
--

CREATE TABLE `student_module` (
  `module_ID` int(11) NOT NULL,
  `student_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_module`
--

INSERT INTO `student_module` (`module_ID`, `student_ID`) VALUES
(1001, 1401001),
(1001, 1401010),
(1001, 1401020),
(1001, 1401030),
(1001, 1402002),
(1005, 1404004),
(1005, 1403003),
(1005, 1404040),
(1005, 1405005),
(1005, 1405050),
(1008, 1405050),
(1008, 1406949),
(1008, 1404004),
(1009, 1408008),
(6001, 1406952),
(2019, 1407007),
(2001, 1405005),
(1019, 1407070),
(4003, 1409090),
(3006, 1409090),
(3010, 1406952),
(3021, 1408080),
(4014, 1409009),
(4014, 1401020),
(3012, 1401020),
(3010, 1401001),
(1019, 1401010),
(1001, 1401030),
(3006, 1406060),
(2004, 1405050),
(1009, 1401020),
(6001, 1408080),
(2004, 1407007),
(2001, 1401001),
(1008, 1404040);

-- --------------------------------------------------------

--
-- Table structure for table `student_password`
--

CREATE TABLE `student_password` (
  `stud_ID` int(11) NOT NULL,
  `password` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_password`
--

INSERT INTO `student_password` (`stud_ID`, `password`) VALUES
(1401001, '3702685c23969f5b581a3af26d96e699e2462b7bc814e735ca1075093a88b204'),
(1401010, 'e8231f25920209a31774f698a388cffb042acb7127b0d1e57a6c1136143cec3a'),
(1401020, '17d90d15988d5699adf977244877d4d1a9fc16dc6fa71cebdb9b50baded7d006'),
(1401030, '183bbc6a0b317f0079ea62f9d5bebbe9aab0c86054a51b5abb5063c203a32cc7'),
(1402002, '624cecbc414b98494a2d503a4f339940b1bf36aced5a49aca676449629c1e2ad'),
(1403003, 'f6e2ef0f69f539aa06ddfb725d47dc0d0337e9267fcaeaf67eb37540c94efc59'),
(1404004, '197fab2d1505743e6ee502482c2ee324600913b9edd35d7a58c1d2419abd8f2a'),
(1404040, 'b537e73d97c445bb15249a1c44aecdb7c63d3503d058f56c10c15cfd284db36e'),
(1405005, 'c004d0b3e44ae23dd9f1b602d4a43faa3f38a3617248fa79a630df1628d94fdc'),
(1405050, '8f0d9a6eeb48ffd13fbf8e276dddb794e80b8c28f49e93c878cf9385a187068d'),
(1406006, '2f35c270b31d0b3548f82666c903a07fc75b1e3911a204b1b853d3c20e056b03'),
(1406060, 'c34b514e9aa326a6b5f97958c180f406e27c7cb785992e67ef68a515e905634e'),
(1406949, 'e449c19dcdf0d27bfaaeda273c31a1784f6de0766139f8f83364e747f6b28d14'),
(1406952, 'eb6ea6ee2844c2d1a1fceb43477440a1d0a0b6b66476232564d5a72b8038ad68'),
(1407007, 'd46c7e08981854bb8ba228a84b397751976d827a0d8a0b2af532963cdca2ab40'),
(1407070, '9c2535005c19f31aa49d0c6bd20630aa41ba13d8cea5b75641057c1fab5cb8ac'),
(1408008, '0f7e2973e7d2cef6eef4af551f46b58881011cdd11795ab1187a5a7d73dec9ff'),
(1408080, '02bc7e5e3dc1ecca88de168f9a8fb9cd3709df23ecbb95d59954cbcfe3d17839'),
(1409009, 'ba1d32c2bd4c75da3361e9b824aa7d6a3b5a926fdf2aa084f8ffed616dbeafe1'),
(1409090, '8d7df5d734e711a7c39c1a4a29dcc13aaff1e9ae285b51fc97d8bfd3b365331e');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`Emp_ID`);

--
-- Indexes for table `employee_enquiry`
--
ALTER TABLE `employee_enquiry`
  ADD PRIMARY KEY (`emp_ID`),
  ADD KEY `fk_ticket_ID` (`ticket_ID`);

--
-- Indexes for table `employee_password`
--
ALTER TABLE `employee_password`
  ADD PRIMARY KEY (`emp_ID`);

--
-- Indexes for table `enquiry`
--
ALTER TABLE `enquiry`
  ADD PRIMARY KEY (`ticket_ID`);

--
-- Indexes for table `FeePayment`
--
ALTER TABLE `FeePayment`
  ADD PRIMARY KEY (`fee_ID`),
  ADD KEY `fk_student` (`student_ID`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`module_ID`),
  ADD KEY `fk_level` (`level`);

--
-- Indexes for table `module_level`
--
ALTER TABLE `module_level`
  ADD PRIMARY KEY (`level`);

--
-- Indexes for table `Owed_Payment`
--
ALTER TABLE `Owed_Payment`
  ADD KEY `fk_student_name` (`stud_ID`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`stud_ID`);

--
-- Indexes for table `student_enquiry`
--
ALTER TABLE `student_enquiry`
  ADD PRIMARY KEY (`stud_ID`),
  ADD KEY `fkey` (`ticket_ID`);

--
-- Indexes for table `student_module`
--
ALTER TABLE `student_module`
  ADD KEY `fk_stud_module` (`module_ID`),
  ADD KEY `fk_studentName` (`student_ID`);

--
-- Indexes for table `student_password`
--
ALTER TABLE `student_password`
  ADD PRIMARY KEY (`stud_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee_enquiry`
--
ALTER TABLE `employee_enquiry`
  ADD CONSTRAINT `fk_employee_id` FOREIGN KEY (`emp_ID`) REFERENCES `employee` (`Emp_ID`),
  ADD CONSTRAINT `fk_ticket_ID` FOREIGN KEY (`ticket_ID`) REFERENCES `enquiry` (`ticket_ID`);

--
-- Constraints for table `employee_password`
--
ALTER TABLE `employee_password`
  ADD CONSTRAINT `fk_emp_id` FOREIGN KEY (`emp_ID`) REFERENCES `employee` (`Emp_ID`);

--
-- Constraints for table `FeePayment`
--
ALTER TABLE `FeePayment`
  ADD CONSTRAINT `fk_student` FOREIGN KEY (`student_ID`) REFERENCES `student` (`stud_ID`);

--
-- Constraints for table `module`
--
ALTER TABLE `module`
  ADD CONSTRAINT `fk_level` FOREIGN KEY (`level`) REFERENCES `module_level` (`level`);

--
-- Constraints for table `Owed_Payment`
--
ALTER TABLE `Owed_Payment`
  ADD CONSTRAINT `fk_student_name` FOREIGN KEY (`stud_ID`) REFERENCES `student` (`stud_ID`);

--
-- Constraints for table `student_enquiry`
--
ALTER TABLE `student_enquiry`
  ADD CONSTRAINT `fk_student_enquiry` FOREIGN KEY (`stud_ID`) REFERENCES `student` (`stud_ID`),
  ADD CONSTRAINT `fkey` FOREIGN KEY (`ticket_ID`) REFERENCES `enquiry` (`ticket_ID`);

--
-- Constraints for table `student_module`
--
ALTER TABLE `student_module`
  ADD CONSTRAINT `fk_stud_module` FOREIGN KEY (`module_ID`) REFERENCES `module` (`module_ID`),
  ADD CONSTRAINT `fk_studentName` FOREIGN KEY (`student_ID`) REFERENCES `student` (`stud_ID`);

--
-- Constraints for table `student_password`
--
ALTER TABLE `student_password`
  ADD CONSTRAINT `fk_student_pwd` FOREIGN KEY (`stud_ID`) REFERENCES `student` (`stud_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
