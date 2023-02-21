import { IAddress } from 'app/shared/model/address.model';
import { IStorageCard } from 'app/shared/model/storage-card.model';
import { ITransferDocument } from 'app/shared/model/transfer-document.model';
import { ICensusDocument } from 'app/shared/model/census-document.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IStorage {
  id?: number;
  name?: string | null;
  code?: string;
  address?: IAddress;
  storageCards?: IStorageCard[] | null;
  receiveds?: ITransferDocument[] | null;
  dispatcheds?: ITransferDocument[] | null;
  censusDocuments?: ICensusDocument[] | null;
  company?: ICompany;
}

export const defaultValue: Readonly<IStorage> = {};
