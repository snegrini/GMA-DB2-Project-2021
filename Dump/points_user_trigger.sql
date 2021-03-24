CREATE DEFINER=`dev`@`localhost` TRIGGER `entry_AFTER_UPDATE` AFTER UPDATE ON `entry` FOR EACH ROW BEGIN
    IF NEW.Points <> OLD.Points THEN
		UPDATE `user` u
		SET u.Points = u.Points - OLD.Points + NEW.Points
		WHERE u.Id = NEW.UserId;
	END IF;
END