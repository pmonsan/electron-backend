/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailContractUpdateComponent } from 'app/entities/detail-contract/detail-contract-update.component';
import { DetailContractService } from 'app/entities/detail-contract/detail-contract.service';
import { DetailContract } from 'app/shared/model/detail-contract.model';

describe('Component Tests', () => {
  describe('DetailContract Management Update Component', () => {
    let comp: DetailContractUpdateComponent;
    let fixture: ComponentFixture<DetailContractUpdateComponent>;
    let service: DetailContractService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailContractUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetailContractUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetailContractUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailContractService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetailContract(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetailContract();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
