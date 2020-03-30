export interface IDetailContract {
  id?: number;
  comment?: string;
  active?: boolean;
  contractId?: number;
}

export class DetailContract implements IDetailContract {
  constructor(public id?: number, public comment?: string, public active?: boolean, public contractId?: number) {
    this.active = this.active || false;
  }
}
