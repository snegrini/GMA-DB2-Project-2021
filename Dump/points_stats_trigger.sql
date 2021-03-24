CREATE DEFINER=`dev`@`localhost` TRIGGER `stats_AFTER_INSERT` AFTER INSERT ON `stats` FOR EACH ROW BEGIN
    UPDATE `entry` e
    SET e.Points = e.Points + 2 * ((NOT ISNULL(NEW.Age)) + (NOT ISNULL(NEW.Sex)) + (NOT ISNULL(NEW.ExpertiseLevel)))
    WHERE e.Id = NEW.EntryId;
END