import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgAccount } from 'app/shared/model/pg-account.model';
import { PgAccountService } from './pg-account.service';

@Component({
  selector: 'jhi-pg-account-delete-dialog',
  templateUrl: './pg-account-delete-dialog.component.html'
})
export class PgAccountDeleteDialogComponent {
  pgAccount: IPgAccount;

  constructor(protected pgAccountService: PgAccountService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgAccountListModification',
        content: 'Deleted an pgAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-account-delete-popup',
  template: ''
})
export class PgAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgAccount = pgAccount;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-account', { outlets: { popup: null } }]);
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
