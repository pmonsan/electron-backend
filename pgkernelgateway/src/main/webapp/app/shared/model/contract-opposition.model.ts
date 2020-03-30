import { Moment } from 'moment';

export interface IContractOpposition {
  id?: number;
  number?: string;
  isCustomerInitiative?: boolean;
  oppositionDate?: Moment;
  oppositionReason?: string;
  comment?: string;
  active?: boolean;
  contractId?: number;
}

export class ContractOpposition implements IContractOpposition {
  constructor(
    public id?: number,
    public number?: string,
    public isCustomerInitiative?: boolean,
    public oppositionDate?: Moment,
    public oppositionReason?: string,
    public comment?: string,
    public active?: boolean,
    public contractId?: number
  ) {
    this.isCustomerInitiative = this.isCustomerInitiative || false;
    this.active = this.active || false;
  }
}
