import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoanInstalment } from 'app/shared/model/loan-instalment.model';
import { LoanInstalmentService } from './loan-instalment.service';

@Component({
  selector: 'jhi-loan-instalment-delete-dialog',
  templateUrl: './loan-instalment-delete-dialog.component.html'
})
export class LoanInstalmentDeleteDialogComponent {
  loanInstalment: ILoanInstalment;

  constructor(
    protected loanInstalmentService: LoanInstalmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.loanInstalmentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'loanInstalmentListModification',
        content: 'Deleted an loanInstalment'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-loan-instalment-delete-popup',
  template: ''
})
export class LoanInstalmentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loanInstalment }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LoanInstalmentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.loanInstalment = loanInstalment;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/loan-instalment', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/loan-instalment', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
