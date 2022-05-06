ALTER TABLE probe_manager.probe ADD CONSTRAINT constraint_probe_x_y_planetid_active UNIQUE (x, y, planet_id, active);
