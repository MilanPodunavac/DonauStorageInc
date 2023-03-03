import { ICompany } from 'app/shared/model/company.model';
import { ICensusDocument } from 'app/shared/model/census-document.model';
import { IStorageCard } from 'app/shared/model/storage-card.model';
import { ITransferDocument } from 'app/shared/model/transfer-document.model';

export interface IBusinessYear {
  id?: number;
  yearCode?: string;
  completed?: boolean;
  company?: ICompany;
  censusDocuments?: ICensusDocument[] | null;
  storageCards?: IStorageCard[] | null;
  transfers?: ITransferDocument[] | null;
}

export const defaultValue: Readonly<IBusinessYear> = {
  completed: false,
};
