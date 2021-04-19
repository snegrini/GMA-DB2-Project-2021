CREATE DEFINER=`dev`@`localhost` TRIGGER `answer_BEFORE_INSERT` BEFORE INSERT ON `answer` FOR EACH ROW BEGIN
    IF (SELECT COUNT(*) FROM `offensiveword` WHERE LOWER(NEW.Answer) REGEXP CONCAT('\\b', LOWER(word), '\\b')) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Offensive word detected!';
    END IF;
END