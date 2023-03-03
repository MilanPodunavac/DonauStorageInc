import { ICity } from 'app/shared/model/city.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { IStorage } from 'app/shared/model/storage.model';

export interface IAddress {
  id?: number;
  streetName?: string;
  streetCode?: string;
  postalCode?: string;
  city?: ICity;
  employee?: IEmployee | null;
  legalEntity?: ILegalEntity | null;
  storage?: IStorage | null;
}

export const defaultValue: Readonly<IAddress> = {};
