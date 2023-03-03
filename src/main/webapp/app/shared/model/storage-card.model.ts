import { IStorageCardTraffic } from 'app/shared/model/storage-card-traffic.model';
import { IBusinessYear } from 'app/shared/model/business-year.model';
import { IResource } from 'app/shared/model/resource.model';
import { IStorage } from 'app/shared/model/storage.model';

export interface IStorageCard {
  id?: string;
  startingAmount?: number;
  receivedAmount?: number;
  dispatchedAmount?: number;
  totalAmount?: number | null;
  startingValue?: number;
  receivedValue?: number;
  dispatchedValue?: number;
  totalValue?: number | null;
  price?: number;
  traffic?: IStorageCardTraffic[] | null;
  businessYear?: IBusinessYear;
  resource?: IResource;
  storage?: IStorage;
}

export const defaultValue: Readonly<IStorageCard> = {};
