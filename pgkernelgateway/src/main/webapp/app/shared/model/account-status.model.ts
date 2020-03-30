export interface IAccountStatus {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class AccountStatus implements IAccountStatus {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
