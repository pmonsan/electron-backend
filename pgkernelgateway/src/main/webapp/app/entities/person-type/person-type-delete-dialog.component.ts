import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonType } from 'app/shared/model/person-type.model';
import { PersonTypeService } from './person-type.service';

@Component({
  selector: 'jhi-person-type-delete-dialog',
  templateUrl: './person-type-delete-dialog.component.html'
})
export class PersonTypeDeleteDialogComponent {
  personType: IPersonType;

  constructor(
    protected personTypeService: PersonTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'personTypeListModification',
        content: 'Deleted an personType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-person-type-delete-popup',
  template: ''
})
export class PersonTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PersonTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.personType = personType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/person-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/person-type', { outlets: { popup: null } }]);
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
