export interface IMeasurementUnit {
  id?: number;
  name?: string | null;
  abbreviation?: string | null;
}

export const defaultValue: Readonly<IMeasurementUnit> = {};
