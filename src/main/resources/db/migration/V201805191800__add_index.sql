ALTER TABLE `word` ADD UNIQUE INDEX `prefix_unique` (`prefix` ASC, `word` ASC);
ALTER TABLE `word` ADD INDEX `prefix_index` (`prefix`(2) ASC);;
