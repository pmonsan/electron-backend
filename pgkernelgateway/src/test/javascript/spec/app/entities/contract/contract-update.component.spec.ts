/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ContractUpdateComponent } from 'app/entities/contract/contract-update.component';
import { ContractService } from 'app/entities/contract/contract.service';
import { Contract } from 'app/shared/model/contract.model';

describe('Component Tests', () => {
  describe('Contract Management Update Component', () => {
    let comp: ContractUpdateComponent;
    let fixture: ComponentFixture<ContractUpdateComponent>;
    let service: ContractService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ContractUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContractUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contract(123);
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
        const entity = new Contract();
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
