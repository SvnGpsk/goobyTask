-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 06. Okt 2016 um 22:46
-- Server-Version: 10.1.13-MariaDB
-- PHP-Version: 5.5.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `users`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups`
--

CREATE TABLE `groups` (
  `groupid` int(11) NOT NULL,
  `groupname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `groups`
--

INSERT INTO `groups` (`groupid`, `groupname`) VALUES
(14, 'Bartalomäo'),
(15, 'Lauras Projekt');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `taskgrouprelation`
--

CREATE TABLE `taskgrouprelation` (
  `taskname` varchar(100) NOT NULL,
  `groupid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `taskgrouprelation`
--

INSERT INTO `taskgrouprelation` (`taskname`, `groupid`) VALUES
('HuanTask', 14),
('Hendrik', 14),
('Alex', 14),
('sdfsdf', 14),
('sfsdf', 14),
('Essen machen', 15),
('Abwaschen', 15),
('Abtrocknen', 15);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tasks`
--

CREATE TABLE `tasks` (
  `taskname` varchar(50) NOT NULL,
  `taskworker` varchar(50) NOT NULL,
  `taskstate` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `tasks`
--

INSERT INTO `tasks` (`taskname`, `taskworker`, `taskstate`) VALUES
('Abtrocknen', 'Sven', 'done'),
('Abwaschen', 'Laura', 'done'),
('Alex', 'Alex', 'check'),
('Essen machen', 'Laura', 'done'),
('Hendrik', 'Hendrik', 'todo'),
('HuanTask', 'Bartal', 'inprogress'),
('sdfsdf', 'sfsdf', 'inprogress'),
('sfsdf', 'sdfsfs', 'check');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`username`, `password`, `email`) VALUES
('Laura', 'password', 'laura@familie-jux.de'),
('test', 'test', 'test@test.test'),
('test1', 'test1', 'hasdhasda');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `usergrouprelation`
--

CREATE TABLE `usergrouprelation` (
  `userid` varchar(50) NOT NULL,
  `groupid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `usergrouprelation`
--

INSERT INTO `usergrouprelation` (`userid`, `groupid`) VALUES
('test', 14),
('Laura', 15),
('test', 15);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`groupid`),
  ADD UNIQUE KEY `groupid` (`groupid`);

--
-- Indizes für die Tabelle `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`taskname`),
  ADD UNIQUE KEY `taskname` (`taskname`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `groups`
--
ALTER TABLE `groups`
  MODIFY `groupid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
