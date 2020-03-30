export interface IAccountType {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class AccountType implements IAccountType {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
