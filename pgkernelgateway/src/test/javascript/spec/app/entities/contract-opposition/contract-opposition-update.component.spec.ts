/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ContractOppositionUpdateComponent } from 'app/entities/contract-opposition/contract-opposition-update.component';
import { ContractOppositionService } from 'app/entities/contract-opposition/contract-opposition.service';
import { ContractOpposition } from 'app/shared/model/contract-opposition.model';

describe('Component Tests', () => {
  describe('ContractOpposition Management Update Component', () => {
    let comp: ContractOppositionUpdateComponent;
    let fixture: ComponentFixture<ContractOppositionUpdateComponent>;
    let service: ContractOppositionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ContractOppositionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContractOppositionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractOppositionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractOppositionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContractOpposition(123);
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
        const entity = new ContractOpposition();
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
