import { ICensusDocument } from 'app/shared/model/census-document.model';
import { IResource } from 'app/shared/model/resource.model';

export interface ICensusItem {
  id?: number;
  amount?: number;
  censusDocument?: ICensusDocument;
  resource?: IResource;
}

export const defaultValue: Readonly<ICensusItem> = {};
