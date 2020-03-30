import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgModule } from 'app/shared/model/pg-module.model';
import { PgModuleService } from './pg-module.service';

@Component({
  selector: 'jhi-pg-module-delete-dialog',
  templateUrl: './pg-module-delete-dialog.component.html'
})
export class PgModuleDeleteDialogComponent {
  pgModule: IPgModule;

  constructor(protected pgModuleService: PgModuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgModuleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgModuleListModification',
        content: 'Deleted an pgModule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-module-delete-popup',
  template: ''
})
export class PgModuleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgModule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgModuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgModule = pgModule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-module', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-module', { outlets: { popup: null } }]);
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
