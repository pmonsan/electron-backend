export interface IOperationStatus {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class OperationStatus implements IOperationStatus {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
