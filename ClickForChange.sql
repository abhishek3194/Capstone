-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 01, 2016 at 11:34 PM
-- Server version: 5.5.50-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ClickForChange`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` varchar(100) NOT NULL,
  `prob_id` varchar(50) NOT NULL,
  `user_id` varchar(10) NOT NULL,
  `comment` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `problems`
--

CREATE TABLE IF NOT EXISTS `problems` (
  `prob_id` varchar(50) NOT NULL,
  `prob_type` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `user_id` varchar(10) NOT NULL,
  `date_time` datetime NOT NULL,
  `num_reactions` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `problems`
--

INSERT INTO `problems` (`prob_id`, `prob_type`, `location_id`, `user_id`, `date_time`, `num_reactions`) VALUES
('9478088896_prob_1', 0, 0, '9478088896', '2016-09-29 07:29:36', 0),
('9478088896_prob_2', 0, 0, '9478088896', '2016-09-29 07:32:21', 0),
('9478088896_prob_3', 0, 0, '9478088896', '2016-09-29 07:35:17', 0),
('9478088896_prob_4', 0, 0, '9478088896', '2016-09-29 07:36:36', 0),
('9478088896_prob_5', 0, 0, '9478088896', '2016-09-29 07:39:47', 0),
('9478088896_prob_6', 0, 0, '9478088896', '2016-09-29 07:41:37', 0),
('9478088896_prob_7', 0, 0, '9478088896', '2016-09-29 07:43:29', 0),
('9478088896_prob_8', 0, 0, '9478088896', '2016-09-29 07:45:27', 0),
('9478088896_prob_9', 0, 0, '9478088896', '2016-09-29 07:51:17', 0),
('9478088896_prob_10', 0, 0, '9478088896', '2016-09-29 07:54:11', 0),
('9478088896_prob_11', 0, 0, '9478088896', '2016-09-29 07:56:55', 0),
('_prob_', 0, 0, '', '2016-09-29 08:01:09', 0),
('_prob_', 0, 0, '', '2016-09-29 08:01:44', 0),
('_prob_', 0, 0, '', '2016-09-29 08:01:55', 0),
('_prob_', 0, 0, '', '2016-09-29 08:01:58', 0),
('_prob_', 0, 0, '', '2016-09-29 08:02:25', 0),
('1_prob_1', 0, 0, '1', '2016-09-29 08:46:00', 0),
('1_prob_2', 0, 0, '1', '2016-09-29 08:49:23', 0),
('9478088896_prob_12', 0, 0, '9478088896', '2016-09-29 17:12:22', 0),
('9478088896_prob_13', 0, 0, '9478088896', '2016-09-29 17:16:17', 0),
('9478088896_prob_14', 0, 0, '9478088896', '2016-09-29 18:01:06', 0),
('9478088896_prob_15', 0, 0, '9478088896', '2016-09-29 18:33:37', 0),
('9478088896_prob_16', 0, 0, '9478088896', '2016-09-29 18:55:46', 0),
('1_prob_3', 0, 0, '1', '2016-09-29 19:07:01', 0),
('1_prob_4', 0, 0, '1', '2016-09-29 19:13:41', 0),
('1_prob_5', 0, 0, '1', '2016-09-29 19:27:57', 0),
('9478088896_prob_17', 0, 0, '9478088896', '2016-09-30 07:34:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `problem_types`
--

CREATE TABLE IF NOT EXISTS `problem_types` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `reactions`
--

CREATE TABLE IF NOT EXISTS `reactions` (
  `prob_id` varchar(50) NOT NULL,
  `user_id` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `UserDetails`
--

CREATE TABLE IF NOT EXISTS `UserDetails` (
  `username` varchar(10) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `email_addr` varchar(100) NOT NULL,
  `mobile_num` varchar(10) NOT NULL,
  `prob_num` int(11) NOT NULL DEFAULT '0',
  UNIQUE KEY `mobile_num` (`mobile_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `UserDetails`
--

INSERT INTO `UserDetails` (`username`, `password`, `name`, `gender`, `email_addr`, `mobile_num`, `prob_num`) VALUES
('1', '1', '', '', '', '1', 5),
('123456789', 'bansal', 'Bansal', 'M', 'hello.abc.in', '123456789', 0),
('9041092408', 'pass', 'Hello', 'F', 'abc@gmail.com', '9041092408', 0),
('9478088896', 'password', 'Poornima', 'F', 'poornima10b@gmail.com', '9478088896', 17),
('9906019608', 'pass', 'Hello', 'F', 'abc@gmail.com', '9906019608', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
