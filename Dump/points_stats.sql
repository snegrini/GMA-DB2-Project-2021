CREATE DEFINER=`dev`@`localhost` TRIGGER `stats_AFTER_INSERT` AFTER INSERT ON `stats` FOR EACH ROW BEGIN
    UPDATE `user` u
    SET u.Points = u.Points + 2 * ((NOT ISNULL(NEW.Age)) + (NOT ISNULL(NEW.Sex)) + (NOT ISNULL(NEW.ExpertiseLevel)))
    WHERE u.Id = (SELECT e.UserId FROM `entry` e WHERE e.Id = NEW.EntryId);
END