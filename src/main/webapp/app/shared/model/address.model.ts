import { ICity } from 'app/shared/model/city.model';

export interface IAddress {
  id?: number;
  streetName?: string;
  streetCode?: string;
  postalCode?: string;
  city?: ICity;
}

export const defaultValue: Readonly<IAddress> = {};
