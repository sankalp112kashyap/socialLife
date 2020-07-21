-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 03, 2019 at 10:55 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `socialLifeDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT 'Hello There',
  `dob` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `profile_pic_path` varchar(255) DEFAULT 'assets/img/default_pic.png'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `first_name`, `last_name`, `sex`, `email`, `status`, `dob`, `created_at`, `profile_pic_path`) VALUES
(1, 'rishabh', '1234', 'Rishabh', 'Jaiswal', 'Male', 'rraj411coding@gmail.com', 'Hello There', '2019-04-03 17:54:40', '2019-04-03 17:54:40', 'assets/img/default_pic.png'),
(2, 'iamsrk', '12345', 'Shahrukh', 'Khan', 'Male', 'iamsrk411@gmail.com', 'Hello There', '2019-04-03 18:36:24', '2019-04-03 18:36:24', 'assets/img/default_pic.png'),
(3, 'robertdowneyjr', '12345', 'Robert', 'Downey', 'Male', 'rdj@gmail.com', 'Hello There', '2019-04-03 19:27:42', '2019-04-03 19:27:42', 'assets/img/default_pic.png'),
(4, 'bond007', '12345', 'James', 'Bond', 'Male', 'jamesbond@gmail.com', 'Hello There', '2019-04-03 19:34:36', '2019-04-03 19:34:36', 'assets/img/default_pic.png'),
(5, 'narendramodi', '12345', 'Narendra', 'Modi', 'Male', 'namo@gmail.com', 'Hello There', '2019-04-03 20:25:50', '2019-04-03 20:25:50', 'assets/img/default_pic.png'),
(6, 'pappu', '12345', 'Rahul', 'Gandhi', 'Male', 'rgandhi@gmail.com', 'Hello There', '2019-04-03 20:40:55', '2019-04-03 20:40:55', 'assets/img/default_pic.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;




CREATE TABLE posts (
  post_id INT PRIMARY KEY,
  user_id INT,
  post_text VARCHAR (255) NOT NULL,
  click_count INT DEFAULT 0,
  view_count INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE comments (

)
